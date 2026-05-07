import java.rmi.*;


public interface MultiplyServerInterface extends Remote{
    // This method will be called remotely by client
    double multiply(double d1, double d2) throws RemoteException;
}
