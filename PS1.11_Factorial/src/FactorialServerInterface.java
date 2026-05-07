import java.rmi.*;

public interface FactorialServerInterface extends Remote {
    // Takes a number, returns its factorial
    long factorial(int n) throws RemoteException;
}