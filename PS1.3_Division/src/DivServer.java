import java.rmi.*;
import java.net.*;

public class DivServer {
    public static void main(String[] args) {
        try {
            // Create the remote object (our addition service)
            DivServerImpl divServerImpl = new DivServerImpl();

            // Register it with the RMI Registry under the name "AddServer"
            Naming.rebind("AddServer", divServerImpl);

            System.out.println("AddServer is ready and waiting...");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}