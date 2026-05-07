import java.net.URL;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;


public class Client {
    public static void main(String[] args){
        try{
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter first number: ");
            int a = sc.nextInt();

            System.out.print("Enter second number: ");
            int b = sc.nextInt();

            URL url = new URL("http://localhost:8080/calculator?wsdl");
            QName qname = new QName("http://calculator/", "MyServiceService");

            Service service = Service.create(url, qname);
            MyServiceInterface obj =service.getPort(MyServiceInterface.class);

            System.out.println("Addition = " + obj.add(a, b));
            System.out.println("Subtraction = " + obj.sub(a, b));
            System.out.println("Multiplication = " + obj.multiply(a, b));
            System.out.println("Division = " + obj.div(a, b));
        } catch(Exception e){
            System.out.println("Exception: " + e);
        }
    }
}
