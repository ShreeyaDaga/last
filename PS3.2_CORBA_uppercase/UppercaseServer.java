import UppercaseModule.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

public class UppercaseServer {
    public static void main(String[] args){
        try{
            ORB orb = ORB.init(args, null);

            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            UppercaseImpl obj = new UppercaseImpl();

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(obj);
            Uppercase href = UppercaseHelper.narrow(ref);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            ncRef.rebind(ncRef.to_name("Uppercase"), href);
            System.out.println("Uppercase Server is running...");

            orb.run();

        }
        catch(Exception e){
            System.out.println("Exception: " + e);
        }
    }
}
