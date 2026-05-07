import java.rmi.*;
import java.util.Scanner;

public class VowelClient {
    public static void main(String[] args) {
        try {
            // args[0] = server IP
            String serverIP = args[0];

            // Take full sentence as input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a word or sentence: ");
            String text = scanner.nextLine();

            // Look up the remote object
            String url = "rmi://" + serverIP + "/VowelServer";
            VowelServerInterface vowelServerIntf =
                    (VowelServerInterface) Naming.lookup(url);

            int vowelCount = vowelServerIntf.countVowels(text);

            System.out.println("\nInput text  : " + text);
            System.out.println("Vowel count : " + vowelCount);

            scanner.close();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}