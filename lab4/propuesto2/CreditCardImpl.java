import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class CreditCardImpl extends UnicastRemoteObject implements CreditCard {
    private double balance;

    public CreditCardImpl() throws RemoteException {
        super();
        balance = 0.0;
    }

    @Override
    public void charge(double amount) throws RemoteException {
        if (amount > 0) {
            balance -= amount;
        }
    }

    

    @Override
    public double getBalance() throws RemoteException {
        return balance;
    }

    @Override
    public void setBalance(double balance) throws RemoteException {
        this.balance = balance;
    }
}
