import java.rmi.*;

/**
 * Defines the remote methods that can be invoked by the client.
 * It acts as a contract between client and server.
 */

public interface AddServerInterface extends Remote {
    // This method will be called remotely by the client

    // RemoteException -- Indicates a failure in communication between client and server during RMI
    // occurs when something goes wrong during a remote method call
    double add(double d1, double d2) throws RemoteException;
}