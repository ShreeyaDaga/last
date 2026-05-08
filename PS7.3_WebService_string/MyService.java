import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService(
    endpointInterface="MyServiceInterface",
    targetNamespace="http://string/"
)
public class MyService implements MyServiceInterface {
    public String username(String name){
        String result = "Hello " + name + " !";
        return result;
    }
}
