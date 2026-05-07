import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class FactorialServerImpl extends UnicastRemoteObject implements FactorialServerInterface {
    public FactorialServerImpl() throws RemoteException{
        // constructor
    }

    public int cal_factorial(int n){
        if(n < 0){
            System.out.println("Factorial of a negative number does't exist. Please enter a valid number");
            return -1;
        }
        if (n == 0 | n ==1){
            return 1;
        }
        else{
            int result = 1;
            for(int i = 2; i <= n; i++){
                result *= i;
            }
            return result;
        }
    }
    
}
