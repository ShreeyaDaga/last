import java.rmi.Naming;
import java.util.Scanner;

public class CompareClient {
    public static void main(String[] args){
        try{
            String serverIP = args[0];
            
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter string 1: ");
            String s1 = sc.nextLine();
            System.out.print("Enter string 2: ");
            String s2 = sc.nextLine();

            String rmiURL = "rmi://" + serverIP + "/CompareServer";
            CompareServerInterface compareserverinterface = (CompareServerInterface) Naming.lookup(rmiURL);

            System.out.println("Result: " + compareserverinterface.compare(s1, s2));

            sc.close();

        } catch(Exception e){
            System.out.println("Exception: " + e);
        }
    }
    
}
