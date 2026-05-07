import java.rmi.*;

public interface VowelServerInterface extends Remote {
    // Takes a word/sentence, returns vowel count
    int countVowels(String text) throws RemoteException;
}