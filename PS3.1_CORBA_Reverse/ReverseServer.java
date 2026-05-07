import ReverseModule.*;
import java.util.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;


public class ReverseServer {
    public static void main(String[] args){
        try{
            // start CORBA runtime
            ORB orb = ORB.init(args, null); 

            // orb.resolve_initial_references("RootPOA") - Asks ORB to give me the default POA --> return generic CORBA object reference
            // resolve_initial_references() - gets important CORBA services
            // .narrow(..) - Convert generic CORBA object into actual POA type
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate(); // start accepting client requests

            // Creates actual server object - normal java object
            ReverseServerImpl obj = new ReverseServerImpl();

            // Convert normal Java object into CORBA-accessible remote object.
            // servant_to_reference - Turns servant/server object into remote CORBA reference.
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(obj);
            // Again converting generic CORBA object into specific "Reverse" interface type.
            Reverse href = ReverseHelper.narrow(ref);

            // asks ORB to give it Naming services(phonebook for CORBA objects)
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            // Convert generic CORBA object into NamingContextExt type.
            // NamingContextExt is the object through which we:   register names, search names, manage naming service
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);



            // This line registers the server object in Naming Service.
            // ncRef.to_name("Reverse") --> Converts simple string: "Reverse" into CORBA naming format.
            ncRef.rebind(ncRef.to_name("Reverse"), href);

            System.out.println("Server is ready");

            orb.run();
        } catch(Exception e){
            System.out.println("Exception: " + e);
        }
    }
}
