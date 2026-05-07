import javax.xml.ws.Endpoint;
// SOAP-related classes come under XML packages.
// Endpoint - “Make service available on network.”

public class Server {
    public static void main(String[] args) {

        //Endpoint.publish --> Used to publish/start web service.
        //"http://localhost:8080/test" --> service address
        //new MyService() --> Creates object of service implementation class. 
        // Meaning: “Publish calculator functions written in MyService.”
        Endpoint.publish("http://localhost:8080/test", new MyService());
        System.out.println("Service started...");
    }
} 
