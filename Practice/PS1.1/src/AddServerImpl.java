import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AddServerImpl extends UnicastRemoteObject implements AddServerInterface{

    public AddServerImpl() throws RemoteException{
        // public constructor
    }

    public double add(double a, double b) throws RemoteException {
        System.out.println("Adding numbers: " + a + " + " + b);
        return a + b;
    }
}
