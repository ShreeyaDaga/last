import java.rmi.*;
import java.util.Scanner;

public class FactorialClient {
    public static void main(String[] args){
        try{
            String serverIP = args[0];

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter a number: ");
            int number = sc.nextInt();

            String urlRMI = "rmi://" + serverIP + "/FactorialServer";
            FactorialServerInterface factorialServerInterface = (FactorialServerInterface) Naming.lookup(urlRMI);

            System.out.println("Result: " + factorialServerInterface.cal_factorial(number));
        } catch(Exception e){
            System.out.println("Exception: " + e);
        }


    
    }
}
