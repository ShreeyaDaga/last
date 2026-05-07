import java.rmi.*;
import java.util.*;

public class TempClient {
    public static void main(String[] args){
        try{
            String serverIP = args[0];
            
            System.out.println("Enter temperature in Celsius: ");
            Scanner sc = new Scanner(System.in);
            double celsius = sc.nextDouble();

            String rmiURL = "rmi://" + serverIP + "/TempServer";
            TempServerInterface tempserverinterface = (TempServerInterface) Naming.lookup(rmiURL);

            System.out.println("Fahrenheit: " + tempserverinterface.temp(celsius));
        } catch(Exception e){
            System.out.println("Exception: " + e);
        }
    }
}
