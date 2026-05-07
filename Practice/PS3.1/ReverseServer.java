import ReverseModule.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

public class ReverseServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);

            ReverseImpl obj = new ReverseImpl();

            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(obj);
            Reverse href = ReverseHelper.narrow(ref);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            ncRef.rebind(ncRef.to_name("Reverse"), href);

            orb.run();
            System.out.println("Server has started...");


        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
