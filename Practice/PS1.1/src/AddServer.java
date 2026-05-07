import java.rmi.Naming;

public class AddServer {
    public static void main(String[] args){
        try{
            AddServerImpl addServerImpl = new AddServerImpl();

            Naming.rebind("AddServer", addServerImpl);

            System.out.println("AddServer is waiting...");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
