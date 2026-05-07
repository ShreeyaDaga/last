import java.net.*;
import java.io.*;

public class MasterDaemonSimple {

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(8000);

        System.out.println("Master waiting for slave...");

        Socket s = ss.accept();

        System.out.println("Slave connected!");

        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());

        while (true) {
            
            // Master current time
            long masterTime = System.currentTimeMillis();

            // Ask slave for time
            out.writeUTF("SEND_TIME");

            // Receive slave time
            long slaveTime = in.readLong();

            // // Calculate average
            // long average = (masterTime + slaveTime) / 2;

            // // Calculate adjustment for slave
            // long adjustment = average - slaveTime;



            // Calculate difference between slave and master
            long difference = slaveTime - masterTime;

            // Calculate average difference
            // (+1 because master is also included with diff = 0)
            long averageDifference = difference / 2;

            // Calculate adjustment for slave
            long adjustment = averageDifference - difference;

            // New master synchronized time
            long newMasterTime = masterTime + averageDifference;



            // Send adjustment
            out.writeLong(adjustment);

            System.out.println("Master Time : " + masterTime);
            System.out.println("Slave Time  : " + slaveTime);
            System.out.println("Difference  : " + difference);
            System.out.println("Average Diff: " + averageDifference);
            System.out.println("New Master Time : " + newMasterTime);
            System.out.println("Adjustment Sent : " + adjustment);

            // Time daemon -> repeat every 5 sec
            Thread.sleep(5000);
        }
    }
}

















// import java.net.*;
// import java.io.*;
// import java.util.*;

// /**
//  * BERKELEY ALGORITHM WITH TIME DAEMON - MASTER
//  * ==============================================
//  * What is a Time Daemon?
//  * A daemon is a background process that runs continuously.
//  * Here, the Time Daemon keeps syncing clocks every few seconds
//  * automatically — just like NTP (Network Time Protocol) works
//  * in real operating systems.
//  *
//  * Flow:
//  * 1. Master starts a Time Daemon thread that runs forever
//  * 2. Daemon waits for slaves to connect
//  * 3. Every SYNC_INTERVAL seconds, daemon wakes up and:
//  *    a. Asks all slaves for their current time
//  *    b. Calculates average difference
//  *    c. Sends correction to each slave
//  * 4. This repeats forever (until you press Ctrl+C)
//  *
//  * Run on: Machine 1 (Master/Server machine)
//  * Command: java MasterDaemon
//  */
// public class MasterDaemon {

//     static final int PORT          = 8000;
//     static final int SYNC_INTERVAL = 10;    // Sync every 10 seconds

//     // synchronized list because multiple threads will access this list
//     static List<Socket>           slaves  = Collections.synchronizedList(new ArrayList<>());
//     static List<DataInputStream>  inputs  = Collections.synchronizedList(new ArrayList<>());
//     static List<DataOutputStream> outputs = Collections.synchronizedList(new ArrayList<>());

//     public static void main(String[] args) throws Exception {

//         ServerSocket serverSocket = new ServerSocket(PORT);
//         System.out.println("=== BERKELEY ALGORITHM - MASTER (DAEMON) ===");
//         System.out.println("Master started. Waiting for slaves to connect...");
//         System.out.println("Master is listening on port " + PORT);
//         System.out.println("Sync interval: " + SYNC_INTERVAL + " seconds");
//         System.out.println("(Press Ctrl+C to stop)\n");

//         // --- Thread 1: Slave Acceptor ---
//         // Runs in background, keeps accepting new slave connections
//         Thread acceptorThread = new Thread(() -> {
//             while (true) {
//                 try {
//                     // Blocks until a slave connects
//                     Socket slave = serverSocket.accept();
//                     DataInputStream  in  = new DataInputStream(slave.getInputStream());
//                     DataOutputStream out = new DataOutputStream(slave.getOutputStream());

//                     slaves.add(slave);
//                     inputs.add(in);
//                     outputs.add(out);

//                     System.out.println("Slave " + slaves.size() + " connected: " + slave.getInetAddress());
//                 } catch (IOException e) {
//                     System.out.println("Acceptor error: " + e.getMessage());
//                     break;
//                 }
//             }
//         });
//         acceptorThread.setDaemon(true); // dies when main program exits
//         acceptorThread.start();

//         // --- Thread 2: Time Daemon ---
//         // Wakes up every SYNC_INTERVAL seconds and runs Berkeley Algorithm
//         Thread timeDaemon = new Thread(() -> {
//             int round = 1;
//             while (true) {
//                 try {
//                     Thread.sleep(SYNC_INTERVAL * 1000);

//                     if (slaves.isEmpty()) {
//                         System.out.println("No slaves connected yet. Waiting...");
//                         continue;
//                     }

//                     runBerkeleyAlgorithm(round);
//                     round++;

//                 } catch (InterruptedException e) {
//                     System.out.println("Daemon interrupted. Stopping.");
//                     break;
//                 } catch (Exception e) {
//                     System.out.println("Error during sync: " + e.getMessage());
//                 }
//             }
//         });
//         // Daemon thread for running every 10 seconds
//         timeDaemon.setDaemon(true);
//         timeDaemon.start();

//         System.out.println("Time Daemon is running...\n");
//         Thread.currentThread().join(); // keeps main thread alive forever so daemon threads keep running
//     }

//     static void runBerkeleyAlgorithm(int round) throws Exception {

//         // ---- PHASE 1: Master records its own time ----
//         long masterTime = System.currentTimeMillis();
//         System.out.println("\n--- SYNC ROUND #" + round + " ---");
//         System.out.println("--- PHASE 1: Collecting Times ---");
//         System.out.println("Master's current time : " + masterTime + " ms");

//         // ---- PHASE 2: Ask each slave for its time ----
//         List<Long>    timeDifferences = new ArrayList<>();
//         List<Integer> failedIndices   = new ArrayList<>();

//         for (int i = 0; i < slaves.size(); i++) {
//             try {
//                 outputs.get(i).writeUTF("TIME_REQUEST");
//                 outputs.get(i).flush();

//                 long slaveTime = inputs.get(i).readLong();
//                 long diff = slaveTime - masterTime;
//                 timeDifferences.add(diff);

//                 System.out.println("Slave " + (i + 1) + " time      : " + slaveTime + " ms  |  Difference: " + diff + " ms");
//             } catch (IOException e) {
//                 System.out.println("Slave " + (i + 1) + " disconnected. Removing.");
//                 failedIndices.add(i);
//                 timeDifferences.add(null);
//             }
//         }

//         // Remove disconnected slaves (in reverse to avoid index shift)
//         for (int i = failedIndices.size() - 1; i >= 0; i--) {
//             int idx = failedIndices.get(i);
//             slaves.remove(idx);
//             inputs.remove(idx);
//             outputs.remove(idx);
//             timeDifferences.remove(idx);
//         }

//         if (timeDifferences.isEmpty()) {
//             System.out.println("No active slaves. Skipping this round.");
//             return;
//         }

//         // ---- PHASE 3: Calculate average difference ----
//         System.out.println("\n--- PHASE 2: Calculating Average ---");
//         long sumDiff = 0;
//         for (long diff : timeDifferences) sumDiff += diff;
//         // +1 for master itself (master diff = 0, but we count it in denominator)
//         long avgDiff = sumDiff / (timeDifferences.size() + 1);

//         System.out.println("Sum of differences : " + sumDiff + " ms");
//         System.out.println("Average difference : " + avgDiff + " ms");

//         // Master adjusts its own clock by avgDiff
//         long newMasterTime = masterTime + avgDiff;
//         System.out.println("Master's new time  : " + newMasterTime + " ms");

//         // ---- PHASE 4: Send each slave its correction ----
//         System.out.println("\n--- PHASE 3: Sending Corrections ---");
//         for (int i = 0; i < timeDifferences.size(); i++) {
//             try {
//                 // adjustment = avgDiff - slaveDiff
//                 // Example: avgDiff = -5ms, slaveDiff = +10ms => slave adjusts by -15ms
//                 long adjustment = avgDiff - timeDifferences.get(i);
//                 outputs.get(i).writeLong(adjustment);
//                 outputs.get(i).flush();
//                 System.out.println("Sent to Slave " + (i + 1) + ": adjustment = " + adjustment + " ms");
//             } catch (IOException e) {
//                 System.out.println("Failed to send to Slave " + (i + 1));
//             }
//         }

//         System.out.println("\nAll clocks are now synchronized! Next sync in " + SYNC_INTERVAL + " seconds.");
//     }
// }