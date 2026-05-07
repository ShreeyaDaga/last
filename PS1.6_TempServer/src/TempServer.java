import java.rmi.*;

public class TempServer {
    public static void main(String[] args) {
        try {
            TempServerImpl tempServerImpl = new TempServerImpl();

            // Register with RMI registry under name "TempServer"
            Naming.rebind("TempServer", tempServerImpl);

            System.out.println("TempServer is ready and waiting...");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}