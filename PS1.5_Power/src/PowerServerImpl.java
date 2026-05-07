import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class PowerServerImpl extends UnicastRemoteObject implements PowerServerInterface{

    public PowerServerImpl() throws RemoteException{
        // constructor
    }

    public double power(double a, double b) throws RemoteException{
        System.out.println("Calculating: " + a + " to the power of " + b);
        return Math.pow(a,b);
    }
}
