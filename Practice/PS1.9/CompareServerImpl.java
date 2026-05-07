import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CompareServerImpl extends UnicastRemoteObject implements CompareServerInterface {
    public CompareServerImpl() throws RemoteException{
        //constructor
    }

    public String compare(String s1, String s2) throws RemoteException{
        int result = s1.compareTo(s2);

        if(result > 0){
            System.out.println("Server: \"" + s1 + "\" is larger");
            return s1;
        }
        if(result < 0){
            System.out.println("Server: \"" + s2 + "\" is larger");
            return s2;
        }
        else{
            System.out.println("Server: Both are equal");
            return "Both strings are equal: " + s1;
        }
    }
}
