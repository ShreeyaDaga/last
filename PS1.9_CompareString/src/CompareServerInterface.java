import java.rmi.*;

public interface CompareServerInterface extends Remote {
    // Takes 2 strings, returns lexicographically largest
    String compareTwoStrings(String s1, String s2) throws RemoteException;
}