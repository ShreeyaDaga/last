import UppercaseModule.*;

import java.util.Scanner;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

public class UppercaseClient {
    public static void main(String[] args) {
        try{
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Uppercase obj = UppercaseHelper.narrow(ncRef.resolve_str("Uppercase"));
            
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a string to convert to uppercase: ");
            String data = sc.nextLine();

            String result = obj.uppercase_string(data);

            System.out.println("Result: " + result);


        }
        catch(Exception e){
            System.out.println();
        }
    }
}
