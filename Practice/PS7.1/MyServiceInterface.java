import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService(targetNamespace = "https://calculator/")
public interface MyServiceInterface {
    @WebMethod  
    public double add(double a, double b);

    @WebMethod  
    public double sub(double a, double b);

    @WebMethod  
    public double multiply(double a, double b);

    @WebMethod  
    public double div(double a, double b);
} 
