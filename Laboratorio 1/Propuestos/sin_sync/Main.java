public class Main {
    public static void main(String[] args) throws InterruptedException {
        CubbyHole cubby = new CubbyHole();
        Productor p = new Productor(cubby, 1);
        Consumidor c = new Consumidor(cubby, 1);
        p.start(); c.start();
        p.join(); c.join();
        System.out.println("FIN sin sincronización");
    }
}