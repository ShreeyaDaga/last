import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;


public interface AddServerInterface extends Remote {

    public double add(double a, double b) throws RemoteException;
}