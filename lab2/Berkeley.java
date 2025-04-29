import java.util.*;
import java.util.concurrent.*;

class Node {
    private final int id;
    private double localTime;
    private final Random rand = new Random();

    public Node(int id, double startTime) {
        this.id = id;
        this.localTime = startTime;
    }

    // Simula latencia de red antes de devolver su hora
    public double getTime() throws InterruptedException {
        int delay = 10 + rand.nextInt(90); 
        Thread.sleep(delay);
        return localTime;
    }

    // Ajusta su reloj local
    public void adjust(double delta) {
        localTime += delta;
    }

    public double getLocalTime() {
        return localTime;
    }

    @Override
    public String toString() {
        return "Nodo " + id + ": " + String.format("%.2f", localTime) + " s";
    }
}

public class Berkeley {
    public static void main(String[] args) throws Exception {
        int n = 4;
        List<Node> nodes = Arrays.asList(
            new Node(0, 100.0),
            new Node(1, 120.0),
            new Node(2, 115.0),
            new Node(3, 135.0)
        );
        ExecutorService exec = Executors.newFixedThreadPool(n);
        System.out.println("Tiempos iniciales:");
        nodes.forEach(node -> System.out.println("  " + node));

        // Sondeo paralelo
        List<Future<Double>> futures = new ArrayList<>();
        List<Double> estimations = new ArrayList<>(Collections.nCopies(n, 0.0));
        for (int i = 0; i < n; i++) {
            final int idx = i;
            futures.add(exec.submit(() -> {
                long start = System.nanoTime();
                double t = nodes.get(idx).getTime();
                long end = System.nanoTime();
                double rtt = (end - start) / 1e9; 
                return t - rtt / 2.0;
            }));
        }
        for (int i = 0; i < n; i++) {
            estimations.set(i, futures.get(i).get());
        }
        exec.shutdown();

        // Cálculo de la media
        double sum = estimations.stream().mapToDouble(Double::doubleValue).sum();
        double avg = sum / n;
        System.out.printf("%nDesviación promedio: %.3f s%n%n", avg);

        // Aplicación de ajustes
        System.out.println("Ajustes realizados:");
        for (int i = 0; i < n; i++) {
            double delta = avg - estimations.get(i);
            nodes.get(i).adjust(delta);
            System.out.println("  " + nodes.get(i) + " (ajuste " + String.format("%.3f", delta) + " s)");
        }
    }
}
