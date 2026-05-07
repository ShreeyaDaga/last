import java.rmi.Naming;
import java.util.Scanner;

public class VowelClient {
    public static void main(String[] args) {
        try {
            serverIP = args[0];

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter string: ");
            String str = sc.nextLine();

            String rmiURL = "rmi://" + serverIP + "/VowelServer";
            VowelServerInterface vowelserverinterface = (VowelServerInterface) Naming.lookup(rmiURL);

            System.out.println("Count of vowels: " + vowelserverinterface.count_vowel(str));


        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
