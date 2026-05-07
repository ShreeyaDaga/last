import java.rmi.*;

/**
 * Looks up the remote object from the registry and invokes its methods.
 */

public class AddClient {
    public static void main(String[] args) {
        try {
            // args[0] = server IP, args[1] = first number, args[2] = second number
            String serverIP = args[0];
            double d1 = Double.parseDouble(args[1]);
            double d2 = Double.parseDouble(args[2]);

            // Build the RMI URL and look up the remote object
            String url = "rmi://" + serverIP + "/AddServer";
            // Naming.lookup -- It returns a stub (proxy object), not the actual server object.
            AddServerInterface addServerIntf = (AddServerInterface) Naming.lookup(url);

            System.out.println("First number  : " + d1);
            System.out.println("Second number : " + d2);
            /**
             * This is not a direct method call.
             * The call is made on a stub (proxy object).
             * The stub:
             *      Converts method call into a request (serialization)
             *      Sends it over the network to the server
             *      Server executes the method
             *      Result is sent back and returned to the client
             */
            System.out.println("Sum           : " + addServerIntf.add(d1, d2));
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}