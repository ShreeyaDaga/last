import java.rmi.*;
import java.rmi.server.*;

/**
 * Provides the actual implementation of the remote method defined in the interface.
 */

// UnicastRemoteObject class throws RemoteException
// so AddServerImpl should also throw RemoteException in its constructor
public class AddServerImpl extends UnicastRemoteObject
        implements AddServerInterface {

    // Constructor must throw RemoteException
    public AddServerImpl() throws RemoteException {
        // UnicastRemoteObject constructor is called automatically
    }

    // Actual logic: adds two numbers and returns result
    public double add(double d1, double d2) throws RemoteException {
        System.out.println("Server: Adding " + d1 + " + " + d2);
        return d1 + d2;
    }
}