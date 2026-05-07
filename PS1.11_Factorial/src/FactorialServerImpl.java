import java.rmi.*;
import java.rmi.server.*;

public class FactorialServerImpl extends UnicastRemoteObject
        implements FactorialServerInterface {

    public FactorialServerImpl() throws RemoteException {
        // required constructor
    }

    public long factorial(int n) throws RemoteException {
        System.out.println("Server: Calculating factorial of " + n);

        // Handle edge cases
        if (n < 0) {
            System.out.println("Server: Negative number, factorial not defined");
            return -1;
        }

        if (n == 0 || n == 1) {
            return 1;
        }

        // Calculate factorial using loop
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result = result * i;
        }

        System.out.println("Server: " + n + "! = " + result);
        return result;
    }
}