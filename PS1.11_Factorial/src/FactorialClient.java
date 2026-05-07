import java.rmi.*;
import java.util.Scanner;

public class FactorialClient {
    public static void main(String[] args) {
        try {
            // args[0] = server IP
            String serverIP = args[0];

            // Take number as input from user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a number: ");
            int n = scanner.nextInt();

            // Look up the remote object
            String url = "rmi://" + serverIP + "/FactorialServer";
            FactorialServerInterface factorialServerIntf =
                    (FactorialServerInterface) Naming.lookup(url);

            long result = factorialServerIntf.factorial(n);

            // Handle negative number response
            if (result == -1) {
                System.out.println("\nInput  : " + n);
                System.out.println("Result : Factorial not defined for negative numbers");
            }
            else {
                System.out.println("\nInput  : " + n);
                System.out.println(n + "!     : " + result);
            }

            scanner.close();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}