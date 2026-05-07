import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PowerServerInterface extends Remote {
    public double power(double a, double b) throws RemoteException;
}
