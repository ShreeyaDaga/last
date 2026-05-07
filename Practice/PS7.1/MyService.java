import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService(
    endpointInterface = "MyServiceInterface",
    targetNamespace = "http://calculator/"
)
public class MyService implements MyServiceInterface{
    public double add(double a, double b) {
        double res = a + b;
        System.out.println("Add: " + res);
        return res;
    }

    public double sub(double a, double b) {
        double res = a - b;
        System.out.println("Sub: " + res);
        return res;
    }

    public double multiply(double a, double b) {
        double res = a * b;
        System.out.println("Mul: " + res);
        return res;
    }

    public double div(double a, double b) {
        double res = a / b;
        System.out.println("Div: " + res);
        return res;
    }
}
