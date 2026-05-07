import java.net.*;
import java.io.*;

/**
 * BERKELEY ALGORITHM - SLAVE NODE
 * ---------------------------------
 * How it works:
 * 1. Slave connects to Master
 * 2. Waits for Master to ask for its time
 * 3. Sends its current system time to Master
 * 4. Receives the adjustment value from Master
 * 5. Applies the adjustment to synchronize its clock
 *
 * Run this on Machine 2 (and Machine 3 if available)
 * Usage: java Slave <master_ip_address>
 */
public class Slave {

    static final int PORT = 8000;

    public static void main(String[] args) throws Exception {

        // Get master's IP from command line argument
        // If not provided, default to localhost (for testing on same machine)
        String masterIP = (args.length > 0) ? args[0] : "localhost";

        System.out.println("=== BERKELEY ALGORITHM - SLAVE ===");
        System.out.println("Connecting to Master at " + masterIP + ":" + PORT + " ...");

        // Step 1: Connect to master
        // Slave initiates the connection
        // Master is already waiting using accept()
        Socket socket = new Socket(masterIP, PORT);
        System.out.println("Connected to Master successfully!\n");

        DataInputStream  in  = new DataInputStream(socket.getInputStream()); // stream to receive from master
        DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // stream to senf to master

        // Step 2: Wait for Master's time request
        String request = in.readUTF();   // reads "TIME_REQUEST"
        System.out.println("Received from Master: " + request);

        // Step 3: Send current time to Master
        long myCurrentTime = System.currentTimeMillis();
        out.writeLong(myCurrentTime);
        out.flush();
        System.out.println("Sent my time to Master: " + myCurrentTime + " ms");

        // Step 4: Receive adjustment from Master
        long adjustment = in.readLong();
        System.out.println("\nReceived adjustment from Master: " + adjustment + " ms");

        // Step 5: Apply the adjustment
        long synchronizedTime = myCurrentTime + adjustment;
        System.out.println("My old time            : " + myCurrentTime + " ms");
        System.out.println("Adjustment to apply    : " + adjustment + " ms");
        System.out.println("My new synchronized time: " + synchronizedTime + " ms");

        if (adjustment > 0) {
            System.out.println("Action: My clock was BEHIND. Moving clock FORWARD by " + adjustment + " ms.");
        } else if (adjustment < 0) {
            System.out.println("Action: My clock was AHEAD. Moving clock BACKWARD by " + Math.abs(adjustment) + " ms.");
        } else {
            System.out.println("Action: My clock was already in sync. No adjustment needed.");
        }

        socket.close();
        System.out.println("\nSynchronization complete. Slave done.");
    }
}