import java.net.*;
import java.io.*;

public class SlaveaemonDSimple {

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 8000);

        System.out.println("Connected to Master!");

        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());

        while (true) {

            // Wait for request from master
            String msg = in.readUTF();

            if (msg.equals("SEND_TIME")) {

                // Current slave time
                long slaveTime = System.currentTimeMillis();

                // Send time to master
                out.writeLong(slaveTime);

                // Receive adjustment
                long adjustment = in.readLong();

                // Updated synchronized time
                long newTime = slaveTime + adjustment;

                System.out.println("\nMy Time       : " + slaveTime);
                System.out.println("Adjustment    : " + adjustment);
                System.out.println("Synchronized Time: " + newTime);
            }
        }
    }
}