import java.rmi.*;
import java.rmi.server.*;

public class DivServerImpl extends UnicastRemoteObject
        implements DivServerInterface {

    // Constructor must throw RemoteException
    public DivServerImpl() throws RemoteException {
        // UnicastRemoteObject constructor is called automatically
    }

    // Actual logic: adds two numbers and returns result
    public double div(double d1, double d2) throws RemoteException {
        System.out.println("Server: Adding " + d1 + " / " + d2);
        return d1 / d2;
    }
}