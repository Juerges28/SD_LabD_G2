import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CreditCard extends Remote {
    void charge(double amount) throws RemoteException;  // carga
    double getBalance() throws RemoteException;  // obtener saldo
    void setBalance(double balance) throws RemoteException;  // setear saldo
}
