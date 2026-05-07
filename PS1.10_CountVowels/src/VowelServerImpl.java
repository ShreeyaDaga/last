import java.rmi.*;
import java.rmi.server.*;

public class VowelServerImpl extends UnicastRemoteObject
        implements VowelServerInterface {

    public VowelServerImpl() throws RemoteException {
        // required constructor
    }

    public int countVowels(String text) throws RemoteException {
        System.out.println("Server: Counting vowels in \"" + text + "\"");

        int count = 0;
        String lowerText = text.toLowerCase(); // handle uppercase vowels too

        for (int i = 0; i < lowerText.length(); i++) {
            char c = lowerText.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;
            }
        }

        System.out.println("Server: Vowel count = " + count);
        return count;
    }
}