import java.rmi.*;
import java.rmi.server.*;


public class MultiplyServerImpl extends UnicastRemoteObject
        implements MultiplyServerInterface {
    // Constructor must throw RemoteException
    public MultiplyServerImpl() throws RemoteException {
        // UnicastRemoteObject constructor is called automatically
    }

    // Actual logic: adds two numbers and returns result
    public double multiply(double d1, double d2) throws RemoteException {
        System.out.println("Server: Multiplying " + d1 + " + " + d2);
        return d1 * d2;
    }
}
