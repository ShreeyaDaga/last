import java.rmi.*;

public class DivClient {
    public static void main(String[] args) {
        try {
            // args[0] = server IP, args[1] = first number, args[2] = second number
            String serverIP = args[0];
            double d1 = Double.parseDouble(args[1]);
            double d2 = Double.parseDouble(args[2]);

            // Build the RMI URL and look up the remote object
            String url = "rmi://" + serverIP + "/AddServer";
            DivServerInterface addServerIntf = (DivServerInterface) Naming.lookup(url);

            System.out.println("First number  : " + d1);
            System.out.println("Second number : " + d2);
            System.out.println("Sum           : " + addServerIntf.div(d1, d2));
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}