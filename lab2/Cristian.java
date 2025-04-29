import java.util.*;
import java.util.concurrent.*;

// Cada cliente tiene un reloj local
class CristianClient {
    private int id;
    private double localTime;

    public CristianClient(int id, double startTime) {
        this.id = id;
        this.localTime = startTime;
    }

    public double getLocalTime() {
        return localTime;
    }

    public void setLocalTime(double newTime) {
        this.localTime = newTime;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "Cliente " + id + ": " + String.format("%.5f", localTime) + " s";
    }
}

// Este servidor tiene la hora correcta
class CristianServer {
    private double serverTime;

    public CristianServer(double startTime) {
        this.serverTime = startTime;
    }

    public double getServerTime() {
        return serverTime;
    }
}


public class Cristian {
    public static void main(String[] args) throws Exception {
        CristianServer server = new CristianServer(100.0); // Tiempo del servidor 

        // Creando clientes
        List<CristianClient> clients = new ArrayList<CristianClient>();
        clients.add(new CristianClient(0, 93.0));
        clients.add(new CristianClient(1, 105.0));
        clients.add(new CristianClient(2, 97.5));
        clients.add(new CristianClient(3, 110.0));

        System.out.println("Tiempos iniciales:");
        for (int i = 0; i < clients.size(); i++) {
            System.out.println("  " + clients.get(i).toString());
        }
        System.out.println("Hora del servidor: " + String.format("%.5f", server.getServerTime()) + " s");

        // ExecutorService gestiona el pool de hilos 
        ExecutorService exec = Executors.newFixedThreadPool(clients.size());
        List<Future<Double>> futures = new ArrayList<Future<Double>>();

        // Cada cliente consulta la hora del servidor
        for (int i = 0; i < clients.size(); i++) {
            futures.add(exec.submit(() -> {
                long t0 = System.nanoTime();
                int latency = 20 + new Random().nextInt(80);
                Thread.sleep(latency);
                double serverTime = server.getServerTime();
                long t1 = System.nanoTime();
                double rtt = (t1 - t0) / 1e9;
                return serverTime + rtt / 2.0;
            }));
        }

        exec.shutdown();

        // Ajustando los relojes de los clientes
        System.out.println("\nAjustes realizados:");
        for (int i = 0; i < clients.size(); i++) {
            double estimatedTime = futures.get(i).get();
            CristianClient client = clients.get(i);
            double oldTime = client.getLocalTime();
            client.setLocalTime(estimatedTime);
            double delta = estimatedTime - oldTime;

            System.out.println("  Cliente " + client.getId() +
                " cambio su reloj en " + String.format("%.5f", delta) +
                " s -> Nuevo tiempo: " + String.format("%.5f", client.getLocalTime()) + " s");
        }

        System.out.println("\nTiempos finales:");
        for (int i = 0; i < clients.size(); i++) {
            System.out.println("  " + clients.get(i).toString());
        }
    }
}
