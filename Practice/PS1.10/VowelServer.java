import java.rmi.Naming;

public class VowelServer {
    public static void main(String[] args) {
        try{
            VowelServerImpl vowelServerImpl = new VowelServerImpl();

            Naming.rebind("VowelServer", vowelServerImpl);
            System.out.println("Server is up and running...");

        } catch(Exception e){
            System.out.println("Exception: " + e);
        }
    }
}
