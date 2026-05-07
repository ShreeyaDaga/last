import java.net.*;
import java.io.*;

public class SlaveSimple {

    public static void main(String[] args) throws Exception {

        // Replace localhost with master's IP on second machine
        Socket s = new Socket("localhost", 8000);

        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());

        // Wait for request
        String msg = in.readUTF();

        if (msg.equals("SEND_TIME")) {

            // Slave current time
            long slaveTime = System.currentTimeMillis();

            // Send time to master
            out.writeLong(slaveTime);

            // Receive adjustment
            long adjustment = in.readLong();

            // Adjusted time
            long synchronizedTime = slaveTime + adjustment;

            System.out.println("\nSlave Time          : " + slaveTime);
            System.out.println("Adjustment Received : " + adjustment);
            System.out.println("Synchronized Time   : " + synchronizedTime);
        }

        s.close();
    }
}