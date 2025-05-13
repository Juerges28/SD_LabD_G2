import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            // buscar tarjeta
            CreditCard card = (CreditCard) Naming.lookup("rmi://localhost/tarjetaPrueba");
            
            // operaciones
            card.setBalance(1000.0);  // seteo de saldo
            System.out.println("Saldo inicial: " + card.getBalance());
            
            card.charge(200.0);  // carga a pagar
            System.out.println("Saldo después del gasto: " + card.getBalance());
        } catch (Exception e) {
            System.out.println("error en cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

