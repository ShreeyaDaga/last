import java.rmi.*;

public class DistClient {
    public static void main(String[] args) {
        try {
            // args[0] = server IP
            // args[1] = miles value
            String serverIP = args[0];
            double miles = Double.parseDouble(args[1]);

            // Look up the remote object
            String url = "rmi://" + serverIP + "/DistServer";
            DistServerInterface tempServerIntf = (DistServerInterface) Naming.lookup(url);

            double km = tempServerIntf.milesToKm(miles);

            System.out.println("Miles    : " + miles + " °C");
            System.out.println("Kilometers : " + km + " °F");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}