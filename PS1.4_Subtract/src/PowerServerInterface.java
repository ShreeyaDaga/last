import java.rmi.*;

public interface PowerServerInterface extends Remote {
    // This method will be called remotely by the client
    double power(double d1, double d2) throws RemoteException;
}