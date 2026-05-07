import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService(targetNamespace = "http://string/")
public interface MyServiceInterface {
    @WebMethod
    public String username(String name);
}
