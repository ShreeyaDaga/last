import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TempServerImpl extends UnicastRemoteObject implements TempServerInterface {
    
    public TempServerImpl() throws RemoteException{
        // public constructor
    }

    public double temp(double celsius) throws RemoteException{
        double fahrenheit = (celsius * 1.8) + 32;
        System.out.println("Calculating value in Fahrenheit..");
        return fahrenheit;
    }
    
}
