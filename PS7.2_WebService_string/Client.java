import java.net.URL;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;


public class Client {
    public static void main(String[] args) {
        try{
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter your name: ");
            String name = sc.nextLine();

            URL url = new URL("http://localhost:8080/string?wsdl");
            QName qname = new QName("http://string/", "MyServiceService");

            Service service = Service.create(url, qname);
            MyServiceInterface obj = service.getPort(MyServiceInterface.class);

            System.out.println("Result: " + obj.username(name));

        }catch(Exception e){
            System.out.println("Exception: " + e);
        }
    }
}
