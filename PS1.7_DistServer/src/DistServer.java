import java.rmi.*;

public class DistServer {
    public static void main(String[] args) {
        try {
            DistServerImpl distServerImpl = new DistServerImpl();

            // Register with RMI registry under name "TempServer"
            Naming.rebind("DistServer", distServerImpl);

            System.out.println("DistServer is ready and waiting...");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}