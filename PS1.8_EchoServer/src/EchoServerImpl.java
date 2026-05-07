import java.rmi.*;
import java.rmi.server.*;

public class EchoServerImpl extends UnicastRemoteObject
        implements EchoServerInterface {

    public EchoServerImpl() throws RemoteException {
        // required constructor
    }

    // Appends name to "Hello, "
    public String sayHello(String name) throws RemoteException {
        System.out.println("Server: Received -> " + name);
        String result = name;
        return result;
    }
}