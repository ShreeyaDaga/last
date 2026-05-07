import java.rmi.*;
import java.rmi.server.*;

public class DistServerImpl extends UnicastRemoteObject
        implements DistServerInterface {

    public DistServerImpl() throws RemoteException {
        // required constructor
    }

    // Formula: F = (C × 9/5) + 32
    public double milesToKm(double miles) throws RemoteException {
        System.out.println("Server: Converting " + miles + "°Miles to Kilometeres");
        double km = (miles * 1.6) ;
        return km;
    }
}