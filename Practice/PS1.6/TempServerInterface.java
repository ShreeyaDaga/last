import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TempServerInterface extends Remote{
    public double temp(double celsius) throws RemoteException;
}