import java.rmi.*;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) {
        try {
            // args[0] = server IP
            // args[1] = name to send
            String serverIP = args[0];
//            String name     = args[1];

            Scanner sc = new Scanner(System.in);
            System.out.printf("Enter your name or sentence: ");
            String name = sc.nextLine();

            // Look up the remote object
            String url = "rmi://" + serverIP + "/EchoServer";
            EchoServerInterface echoServerIntf = (EchoServerInterface) Naming.lookup(url);

            String response = echoServerIntf.sayHello(name);

            System.out.println("Sent    : " + name);
            System.out.println("Received: " + response);

            sc.close();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}