import java.rmi.*;

public class VowelServer {
    public static void main(String[] args) {
        try {
            VowelServerImpl vowelServerImpl = new VowelServerImpl();

            // Register with RMI registry under name "VowelServer"
            Naming.rebind("VowelServer", vowelServerImpl);

            System.out.println("VowelServer is ready and waiting...");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}