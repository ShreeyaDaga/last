package si.client.application;

public class SIClient {
    public static void main(String[] args) {

        org.si.calculator.SIWebService_Service service =
            new org.si.calculator.SIWebService_Service();

        org.si.calculator.SIWebService port =
            service.getSIWebServicePort();

        double principal = 10000;
        double rate      = 5;
        double time      = 3;

        double si = port.calculateSI(principal, rate, time);

        System.out.println("Principal : Rs. " + principal);
        System.out.println("Rate      : "     + rate + "%");
        System.out.println("Time      : "     + time + " years");
        System.out.println("SI        : Rs. " + si);
    }
}