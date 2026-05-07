import java.rmi.*;

public class TempClient {
    public static void main(String[] args) {
        try {
            // args[0] = server IP
            // args[1] = celsius value
            String serverIP = args[0];
            double celsius = Double.parseDouble(args[1]);

            // Look up the remote object
            String url = "rmi://" + serverIP + "/TempServer";
            TempServerInterface tempServerIntf = (TempServerInterface) Naming.lookup(url);

            double fahrenheit = tempServerIntf.celsiusToFahrenheit(celsius);

            System.out.println("Celsius    : " + celsius + " °C");
            System.out.println("Fahrenheit : " + fahrenheit + " °F");
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}