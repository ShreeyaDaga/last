package hello.client.app;

public class HelloClient {

    public static void main(String[] args) {

        // Step 1: Create service
        org.hello.service.HelloWebService_Service service =
            new org.hello.service.HelloWebService_Service();

        // Step 2: Get port (stub)
        org.hello.service.HelloWebService port =
            service.getHelloWebServicePort();

        // Step 3: Call the web service method
        String name1 = "Alice";
        String name2 = "Bob";
        String name3 = "PICT";

        System.out.println("===== Hello Web Service =====");
        System.out.println(port.sayHello(name1));
        System.out.println(port.sayHello(name2));
        System.out.println(port.sayHello(name3));
    }
}