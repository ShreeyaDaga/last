package miles.client.app;

public class MilesConverterClient {

    public static void main(String[] args) {

        // Step 1: Create service
        org.miles.service.MilesConverterWebService_Service service =
            new org.miles.service.MilesConverterWebService_Service();

        // Step 2: Get port (stub)
        org.miles.service.MilesConverterWebService port =
            service.getMilesConverterWebServicePort();

        // Step 3: Call web service and print results
        System.out.println("===== Miles to Kilometer Converter =====");

        double[] milesValues = {1, 5, 10, 50, 100};

        for (double miles : milesValues) {
            double km = port.convertToKilometer(miles);
            System.out.println(miles + " miles = " + km + " km");
        }
    }
}