package si.client.application;

/**
 * Client to consume the Simple Interest Web Service
 */
public class SIClient {

    public static void main(String[] args) {

        // Create service and port
        org.si.calculator.SIWebService_Service service =
                new org.si.calculator.SIWebService_Service();

        org.si.calculator.SIWebService port =
                service.getSIWebServicePort();

        // Test Case 1
        double principal = 10000;
        double rate      = 5;
        double time      = 3;

        double si     = port.calculateSI(principal, rate, time);
        double total  = port.calculateTotalAmount(principal, rate, time);

        System.out.println("=== Simple Interest Calculator ===");
        System.out.println("Principal Amount : Rs. " + principal);
        System.out.println("Rate of Interest : " + rate + "% per annum");
        System.out.println("Time Period      : " + time + " years");
        System.out.println("----------------------------------");
        System.out.println("Simple Interest  : Rs. " + si);
        System.out.println("Total Amount     : Rs. " + total);

        // Test Case 2
        System.out.println("\n=== Test Case 2 ===");
        double si2 = port.calculateSI(50000, 8.5, 2);
        System.out.println("SI for P=50000, R=8.5, T=2 : Rs. " + si2);
    }
}