import java.rmi.*;

public interface FactorialServerInterface extends Remote{
    public int cal_factorial(int number) throws RemoteException;
}
