import java.rmi.*;

public interface EchoServerInterface extends Remote {
    // Takes a name, returns "Hello, <name>!"
    String sayHello(String name) throws RemoteException;
}