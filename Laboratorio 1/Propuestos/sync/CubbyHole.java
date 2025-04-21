public class CubbyHole {
    private int contents;
    private boolean available = false;

    public synchronized int get() {
        while (!available) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        available = false;
        notify();
        return contents;
    }

    public synchronized void put(int value) {
        while (available) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        contents = value;
        available = true;
        notify();
    }
}