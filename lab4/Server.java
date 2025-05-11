import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {

            
            // registro RMI
            LocateRegistry.createRegistry(1099);
            
            // objeto remoto
            CreditCardImpl testCard = new CreditCardImpl();
            
            
            
            // registra el objeto remoto en el RMI Registry
            Naming.rebind("tarjetaPrueba", testCard);
            
            System.out.println("server RMI iniciado y objeto remoto registrado");
        } catch (Exception e) {
            System.out.println("error en server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

