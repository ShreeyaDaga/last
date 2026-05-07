import java.util.Scanner;
import ReverseModule.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

public class ReverseClient {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter String: ");
            String input = sc.nextLine();

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Reverse obj = ReverseHelper.narrow(ncRef.resolve_str("Reverse"));
            String result = obj.reverse_string(input);

            System.out.println("Result: " + result);


        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
