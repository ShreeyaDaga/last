import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args){
        Endpoint.publish("http://localhost:8080/calculator", new MyService());
        System.out.println("Service has started..");
    }
}
