import java.net.*;
import java.io.*;

public class MasterSimple {

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(8000);

        System.out.println("Waiting for slave...");

        Socket s = ss.accept();

        System.out.println("Slave connected!");

        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());

        // Master time
        long masterTime = System.currentTimeMillis();

        // Ask slave for time
        out.writeUTF("SEND_TIME");

        // Receive slave time
        long slaveTime = in.readLong();

        // Berkeley Algorithm
        long difference = slaveTime - masterTime;

        long averageDifference = difference / 2;

        long adjustment = averageDifference - difference;

        long synchronizedMasterTime = masterTime + averageDifference;

        // Send adjustment to slave
        out.writeLong(adjustment);

        System.out.println("\nMaster Time           : " + masterTime);
        System.out.println("Slave Time            : " + slaveTime);
        System.out.println("Average Difference    : " + averageDifference);
        System.out.println("Synchronized Master   : " + synchronizedMasterTime);

        s.close();
        ss.close();
    }
}