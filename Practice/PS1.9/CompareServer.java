import java.rmi.Naming;

public class CompareServer {
    public static void main(String[] args){
        try{
            CompareServerImpl compareserverimpl = new CompareServerImpl();

            Naming.rebind("CompareServer", compareserverimpl);

            System.out.println("Server is up and waiting...");
        } catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }
    
}
