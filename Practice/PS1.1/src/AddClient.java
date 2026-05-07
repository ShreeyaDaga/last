import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AddClient {
    public static void main(String[] args) {
            try{
                String serverIP = args[0];
                double number1 = Double.parseDouble(args[1]);
                double number2 = Double.parseDouble(args[2]);

                String rmiURL = "rmi://" + serverIP + "/AddServer";
                AddServerInterface addServerInterface = (AddServerInterface) Naming.lookup(rmiURL);

                System.out.println("First Number: " + number1);
                System.out.println("Second Number: " + number2);
                System.out.println("Result: " + addServerInterface.add(number1, number2));

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
    }
}
