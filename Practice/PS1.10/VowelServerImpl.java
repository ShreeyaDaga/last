import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VowelServerImpl extends UnicastRemoteObject implements VowelServerInterface{
    public VowelServerImpl() throws RemoteException{
        // constructor
    }

    public int count_vowel(String input){
        int count = 0;
        String lowerText = input.toLowerCase();

        for(int i = 0; i < lowerText.length(); i++){
            char c = lowerText.charAt(i);
            if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'){
                count++;
            }
        }

        return count;
    }

    
}
