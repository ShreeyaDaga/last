import java.rmi.*;

public class PowerClient {
    public static void main(String[] args) {
        try {
            // args[0] = server IP, args[1] = first number, args[2] = second number
            String serverIP = args[0];
            double d1 = Double.parseDouble(args[1]);
            double d2 = Double.parseDouble(args[2]);

            // Build the RMI URL and look up the remote object
            String url = "rmi://" + serverIP + "/AddServer";
            PowerServerInterface powerServerIntf = (PowerServerInterface) Naming.lookup(url);

            System.out.println("Base number  : " + d1);
            System.out.println("Exponent number : " + d2);
            System.out.println("Result           : " + powerServerIntf.power(d1, d2));
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}

