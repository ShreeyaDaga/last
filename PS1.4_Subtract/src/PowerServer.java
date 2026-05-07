import java.rmi.*;
import java.net.*;

public class PowerServer {
    public static void main(String[] args) {
        try {
            // Create the remote object (our addition service)
            PowerServerImpl addServerImpl = new PowerServerImpl();

            // Register it with the RMI Registry under the name "AddServer"
            Naming.rebind("PowerServer", addServerImpl);

            System.out.println("PowerServer is ready and waiting...");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}