import java.rmi.*;

public class TempServer{
    public static void main(String[] args){
        try{
            TempServerImpl tempserverimpl = new TempServerImpl();

            Naming.rebind("TempServer", tempserverimpl);

            System.out.println("Server is up and waiting...");
        }
        catch(Exception e){
            System.out.println("Exception: " + e);
        }

    }
}