import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CompareServerInterface extends Remote {
    public String compare(String s1, String s2) throws RemoteException;
}
