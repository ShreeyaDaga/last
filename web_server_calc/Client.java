import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first number: ");
        double a = sc.nextDouble();

        System.out.print("Enter second number: ");
        double b = sc.nextDouble();

        //Java automatically generates WSDL. Accessible at: http://localhost:8080/test?wsdl
        URL url = new URL("http://localhost:8080/test?wsdl");
        QName qname = new QName("http://test/", "MyServiceService");

        //Qualified name uniquely identify the web service.
        //when many services are present, java needs exact identity. 
        //So Qname have namespace + service Name.


        //Reads WSDL --> finds service using Qname --> Creates service connection object
        //“Connect me to the web service described in this WSDL.”
        // reads WSDL --> finds service --> creates service connection object
        Service service = Service.create(url, qname);
        // "Create proxy object through which -> client can call remote methods -> defined in MyServiceInterface."
        MyServiceInterface obj = service.getPort(MyServiceInterface.class);

        System.out.println("Addition = " + obj.add(a, b));
        System.out.println("Subtraction = " + obj.sub(a, b));
        System.out.println("Multiplication = " + obj.mul(a, b));
        System.out.println("Division = " + obj.div(a, b));
    }
}
