import java.rmi.*;

public class EchoServer {
    public static void main(String[] args) {
        try {
            EchoServerImpl echoServerImpl = new EchoServerImpl();

            // Register with RMI registry under name "EchoServer"
            Naming.rebind("EchoServer", echoServerImpl);

            System.out.println("EchoServer is ready and waiting...");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}