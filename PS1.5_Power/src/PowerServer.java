import java.rmi.Naming;
import java.rmi.RemoteException;

public class PowerServer {
    public static void main(String[] args){
        try{
            PowerServerImpl powerServerImpl = new PowerServerImpl();

            Naming.rebind("PowerServer", powerServerImpl);
            System.out.println("Server is waiting");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }
}
