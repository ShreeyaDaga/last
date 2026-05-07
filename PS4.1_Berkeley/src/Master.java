/**
 *4.1 Implement Berkeley algorithm for clock synchronization using two physical
 *         machines. Perform operations on 2 different machines.
 */

import java.net.*;   // networking imports - Socket, ServerSocket
import java.io.*;
import java.util.*;

/**
 * BERKELEY ALGORITHM - MASTER NODE
 * ---------------------------------
 * How it works:
 * 1. Master asks all slaves: "What time do you have?"
 * 2. Each slave replies with its current time
 * 3. Master calculates the average difference between all clocks
 * 4. Master sends each slave the adjustment it needs to apply
 *
 * Run this on Machine 1 (the server/master machine)
 */

// Defines the master node
    // This machine coordinates synchronisation
public class Master {

    // Port on which master listens for slave connections
    static final int PORT = 8000;

    // List to store connected slave sockets

    // Socket - connection between server and client - communiction channel between 2 machines
    // <Socket> - generic type parameter
    static List<Socket> slaves = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        // Step 1: Start the master server

        // Creates a server which listens for incoming slave connections
        // ServerSocket -- Java class used to create a server
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("=== BERKELEY ALGORITHM - MASTER ===");
        System.out.println("Master started. Waiting for slaves to connect...");
        System.out.println("Master is listening on port " + PORT);

        // Step 2: Accept slave connections
        // For exam: connect 2 slaves (or at least 1 other machine)
        System.out.print("How many slaves will connect? ");
        Scanner sc = new Scanner(System.in);
        int numSlaves = sc.nextInt();

        for (int i = 0; i < numSlaves; i++) {
            // Blocking call
            // Waits until the slave connects
            Socket slave = serverSocket.accept();
            slaves.add(slave); // stores the connection in the list
            System.out.println("Slave " + (i + 1) + " connected: " + slave.getInetAddress());
        }

        System.out.println("\nAll " + numSlaves + " slave(s) connected. Starting synchronization...\n");

        // Step 3: Run the Berkeley Algorithm
        runBerkeleyAlgorithm();

        // Close all the connections
        for (Socket s : slaves) s.close();
        serverSocket.close();
        System.out.println("\nSynchronization complete. Master shutting down.");
    }

    static void runBerkeleyAlgorithm() throws Exception {

        // ---- PHASE 1: Master records its own time ----
        // currentTimeMillis -- gets the current time in milliseconds since Unix Epoch
        long masterTime = System.currentTimeMillis();
        System.out.println("--- PHASE 1: Collecting Times ---");
        System.out.println("Master's current time : " + masterTime + " ms");

        // ---- PHASE 2: Ask each slave for its time ----
        // We store the time difference: (slaveTime - masterTime)
        List<Long> timeDifferences = new ArrayList<>();  // slaveTime - masterTime
        // store streams for each slave
        List<DataInputStream>  inputs  = new ArrayList<>(); // stores input streams of all the slaves [in1, in2, in3]
        List<DataOutputStream> outputs = new ArrayList<>(); // stores output streams of all the slaves [out1, out2, out3]

        for (int i = 0; i < slaves.size(); i++) {
            // creates communication stream for each slave
            // in --> read from slave
            // out --> send to slave
            // Each slaves.get(i) is a Socket
            DataInputStream  in  = new DataInputStream(slaves.get(i).getInputStream());
            DataOutputStream out = new DataOutputStream(slaves.get(i).getOutputStream());
            // save streams for sending adjustments later
            inputs.add(in);
            outputs.add(out);

            // Send "TIME_REQUEST" signal to slave
            // writeUTF() -- write string to an output stream in UTF format
            out.writeUTF("TIME_REQUEST");
            // flush() -- forces data to be sent immediately
            // without it, data might stay in the buffer causing delay
            out.flush();

            // Receive slave's current time
            long slaveTime = in.readLong(); // master waits till Slave sends
            long diff = slaveTime - masterTime;   // positive = slave is ahead, negative = slave is behind
            timeDifferences.add(diff); // store each slave difference - [+5, -7, +1]

            System.out.println("Slave " + (i + 1) + " time      : " + slaveTime + " ms  |  Difference: " + diff + " ms");
        }

        // ---- PHASE 3: Calculate average difference ----
        // Include master's own difference (which is always 0)
        System.out.println("\n--- PHASE 2: Calculating Average ---");
        long sumDiff = 0;
        for (long diff : timeDifferences) sumDiff += diff;
        // +1 for master itself (master diff = 0, but we count it in denominator)
        long avgDiff = sumDiff / (timeDifferences.size() + 1);

        System.out.println("Sum of differences : " + sumDiff + " ms");
        System.out.println("Average difference : " + avgDiff + " ms");

        // Master adjusts its own clock by savgDiff
        long newMasterTime = masterTime + avgDiff;

        // ---- PHASE 4: Send each slave its correction ----
        // System.out.println("\n--- PHASE 3: Sending Corrections ---");
        for (int i = 0; i < slaves.size(); i++) {
            // How much should slave i adjust?
            // adjustment = avgDiff - slaveDiff
            // Example: avgDiff = -5ms, slaveDiff = +10ms => slave adjusts by -15ms
            long adjustment = avgDiff - timeDifferences.get(i);
            outputs.get(i).writeLong(adjustment);  // send correction to slave
            outputs.get(i).flush();
            System.out.println("Sent to Slave " + (i + 1) + ": adjustment = " + adjustment + " ms");
        }

        System.out.println("\nAll clocks are now synchronized!");
    }
}