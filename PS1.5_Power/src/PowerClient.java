import java.rmi.Naming;
import java.util.Scanner;

public class PowerClient {
    public static void main(String[] args){
        try{
            String serverIP = args[0];

            Scanner sc = new Scanner(System.in);

            System.out.println("Enter base number: ");
            double base = sc.nextDouble();

            System.out.println("Enter exponent: ");
            double expo = sc.nextDouble();

            String rmiURL = "rmi://" + serverIP + "PowerServer";
            PowerServerInterface powerServerInterface = (PowerServerInterface) Naming.lookup(rmiURL);

            System.out.println("Result: " + powerServerInterface.power(base, expo));
        }catch (Exception e){
            System.out.println("Exception: " + e);
        }

    }
}
