import java.rmi.*;

public interface TempServerInterface extends Remote {
    // Takes celsius, returns fahrenheit
    double celsiusToFahrenheit(double celsius) throws RemoteException;
}