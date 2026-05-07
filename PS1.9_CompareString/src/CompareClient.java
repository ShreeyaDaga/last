import java.rmi.*;
import java.util.Scanner;

public class CompareClient {
    public static void main(String[] args) {
        try {
            // args[0] = server IP
            String serverIP = args[0];

            // Take both strings as input from user
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter first string  : ");
            String s1 = scanner.nextLine();

            System.out.print("Enter second string : ");
            String s2 = scanner.nextLine();

            // Look up the remote object
            String url = "rmi://" + serverIP + "/StringServer";
            CompareServerInterface stringServerIntf =
                    (CompareServerInterface) Naming.lookup(url);

            String largest = stringServerIntf.compareTwoStrings(s1, s2);

            System.out.println("\nString 1          : " + s1);
            System.out.println("String 2          : " + s2);
            System.out.println("Lexicographic largest : " + largest);

            scanner.close();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}