import ReverseModule.*;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

import java.util.Scanner;

public class ReverseClient {
    public static void main(String[] args){
        try{
            Scanner sc = new Scanner(System.in);

            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Reverse obj = ReverseHelper.narrow(ncRef.resolve_str("Reverse"));

            String input;
            System.out.print("Enter a string: ");
            input = sc.nextLine();

            String result = obj.reverse_string(input);

            System.out.println("Result: " + result);
        } catch(Exception e){
            System.out.println("Exception: " + e);
        }

    }
}
