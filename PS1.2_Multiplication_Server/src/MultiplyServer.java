import java.rmi.*;
import java.net.*;

public class MultiplyServer {
    public static void main(String[] args) {
        try {
            // Create the remote object (our addition service)
            MultiplyServerImpl multiplyServerImpl = new MultiplyServerImpl();

            // Register it with the RMI Registry under the name "AddServer"
            Naming.rebind("MultiplyServer",  multiplyServerImpl);

            System.out.println("MultiplyServer is ready and waiting...");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}