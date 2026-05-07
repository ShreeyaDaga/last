import java.rmi.*;

public class CompareServer {
    public static void main(String[] args) {
        try {
            CompareServerImpl stringServerImpl = new CompareServerImpl();

            // Register with RMI registry under name "StringServer"
            Naming.rebind("StringServer", stringServerImpl);

            System.out.println("StringServer is ready and waiting...");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}