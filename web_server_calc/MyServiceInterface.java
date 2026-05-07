import javax.jws.WebService;  // java extension packages for JavaWebServices
import javax.jws.WebMethod;


// This file defines what services are available.


//→@WebService --> Marks this interface as a web service.
@WebService(targetNamespace = "http://test/")
public interface MyServiceInterface {

    //“There exists a remote method called add() which takes 2 
    // double values and returns double.”
    @WebMethod
    double add(double a, double b);

    @WebMethod
    double sub(double a, double b);

    @WebMethod
    double mul(double a, double b);

    @WebMethod
    double div(double a, double b);
}
