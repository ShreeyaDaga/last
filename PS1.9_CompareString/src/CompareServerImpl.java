import java.rmi.*;
import java.rmi.server.*;

public class CompareServerImpl extends UnicastRemoteObject
        implements CompareServerInterface {

    public CompareServerImpl() throws RemoteException {
        // required constructor
    }

    // compareTo() returns:
    //   positive → s1 comes after s2 lexicographically → s1 is larger
    //   negative → s2 comes after s1 lexicographically → s2 is larger
    //   zero     → both strings are equal
    // (e.g., A < B < ... < Z < a < b < ... < z)
    public String compareTwoStrings(String s1, String s2) throws RemoteException {
        System.out.println("Server: Comparing \"" + s1 + "\" and \"" + s2 + "\"");

        int result = s1.compareTo(s2);

        if (result > 0) {
            System.out.println("Server: \"" + s1 + "\" is larger");
            return s1;
        }
        else if (result < 0) {
            System.out.println("Server: \"" + s2 + "\" is larger");
            return s2;
        }
        else {
            System.out.println("Server: Both strings are equal");
            return "Both strings are equal: " + s1;
        }
    }
}