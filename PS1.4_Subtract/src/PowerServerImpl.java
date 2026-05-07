
import java.rmi.*;
import java.rmi.server.*;

public class PowerServerImpl extends UnicastRemoteObject
        implements PowerServerInterface {

    // Constructor must throw RemoteException
    public PowerServerImpl() throws RemoteException {
        // UnicastRemoteObject constructor is called automatically
    }

    // Actual logic: adds two numbers and returns result
    public double power(double d1, double d2) throws RemoteException {
        System.out.println("Server: Calculating " + d1 + " to the power of " + d2);
        return Math.pow(d1, d2);
    }
}