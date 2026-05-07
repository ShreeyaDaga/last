import javax.jws.WebService;

// The actual implementation/business logic of the web service.

@WebService(         //→ Marks this interface as a web service.
    endpointInterface = "MyServiceInterface",   //“This web service class is implementing methods defined inside MyServiceInterface.”
    targetNamespace = "http://test/"        //Unique identifier for the service. Similar to package naming for avoiding conflicts.
)

//“Use methods defined inside MyServiceInterface.”
public class MyService implements MyServiceInterface {

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

    public double mul(double a, double b) {
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

