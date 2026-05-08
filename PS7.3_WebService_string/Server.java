import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args){
        Endpoint.publish("http://localhost:8080/string", new MyService() );
        System.out.println("Server has started..");
    }
}
