import java.rmi.*;
import java.rmi.server.*;

public class TempServerImpl extends UnicastRemoteObject
        implements TempServerInterface {

    public TempServerImpl() throws RemoteException {
        // required constructor
    }

    // Formula: F = (C × 9/5) + 32
    public double celsiusToFahrenheit(double celsius) throws RemoteException {
        System.out.println("Server: Converting " + celsius + "°C to Fahrenheit");
        double fahrenheit = (celsius * 9.0 / 5.0) + 32.0;
        return fahrenheit;
    }
}