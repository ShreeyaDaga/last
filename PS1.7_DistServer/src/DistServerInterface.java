import java.rmi.*;

public interface DistServerInterface extends Remote {
    // Takes celsius, returns fahrenheit
    double milesToKm(double miles) throws RemoteException;
}