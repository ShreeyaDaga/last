import java.rmi.*;

public interface DivServerInterface extends Remote {
    // This method will be called remotely by the client
    double div(double d1, double d2) throws RemoteException;
}