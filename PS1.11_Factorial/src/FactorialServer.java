import java.rmi.*;

public class FactorialServer {
    public static void main(String[] args) {
        try {
            FactorialServerImpl factorialServerImpl = new FactorialServerImpl();

            // Register with RMI registry under name "FactorialServer"
            Naming.rebind("FactorialServer", factorialServerImpl);

            System.out.println("FactorialServer is ready and waiting...");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}