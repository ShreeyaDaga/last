package calculator.client.app;

public class CalculatorClient {

    public static void main(String[] args) {

        org.calculator.service.CalculatorWebService_Service service =
            new org.calculator.service.CalculatorWebService_Service();

        org.calculator.service.CalculatorWebService port =
            service.getCalculatorWebServicePort();

        double a = 20;
        double b = 5;

        System.out.println("===== Calculator Web Service =====");
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("----------------------------------");
        System.out.println("Add      : " + port.add(a, b));
        System.out.println("Subtract : " + port.subtract(a, b));
        System.out.println("Multiply : " + port.multiply(a, b));
        System.out.println("Divide   : " + port.divide(a, b));

        // Division by zero test
        try {
            port.divide(10, 0);
        } catch (Exception e) {
            System.out.println("Divide/0 : Error - " + e.getMessage());
        }
    }
}