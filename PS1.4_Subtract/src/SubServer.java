import java.rmi.*;
import java.net.*;

/**
 * Creates the remote object and registers it with the RMI registry so that clients can access it.
 */

public class AddServer {
    public static void main(String[] args) {
        try {
            // Create the remote object (our addition service)
            AddServerImpl addServerImpl = new AddServerImpl();

            // Register it with the RMI Registry under the name "AddServer"
            // If an object with the same name already exists, it replaces it.
            Naming.rebind("AddServer", addServerImpl);

            System.out.println("AddServer is ready and waiting...");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}