import java.net.*;
import java.io.*;
import java.util.*;

/**
 * BERKELEY ALGORITHM WITH TIME DAEMON - MASTER
 * ==============================================
 * What is a Time Daemon?
 * A daemon is a background process that runs continuously.
 * Here, the Time Daemon keeps syncing clocks every few seconds
 * automatically — just like NTP (Network Time Protocol) works
 * in real operating systems.
 *
 * Flow:
 * 1. Master starts a Time Daemon thread that runs forever
 * 2. Daemon waits for slaves to connect
 * 3. Every SYNC_INTERVAL seconds, daemon wakes up and:
 *    a. Asks all slaves for their current time
 *    b. Calculates average difference
 *    c. Sends correction to each slave
 * 4. This repeats forever (until you press Ctrl+C)
 *
 * Run on: Machine 1 (Master/Server machine)
 * Command: java MasterDaemon
 */
public class MasterDaemon {

    static final int PORT          = 8000;
    static final int SYNC_INTERVAL = 10;    // Sync every 10 seconds

    // synchronized list because multiple threads will access this list
    static List<Socket>           slaves  = Collections.synchronizedList(new ArrayList<>());
    static List<DataInputStream>  inputs  = Collections.synchronizedList(new ArrayList<>());
    static List<DataOutputStream> outputs = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("=== BERKELEY ALGORITHM - MASTER (DAEMON) ===");
        System.out.println("Master started. Waiting for slaves to connect...");
        System.out.println("Master is listening on port " + PORT);
        System.out.println("Sync interval: " + SYNC_INTERVAL + " seconds");
        System.out.println("(Press Ctrl+C to stop)\n");

        // --- Thread 1: Slave Acceptor ---
        // Runs in background, keeps accepting new slave connections
        Thread acceptorThread = new Thread(() -> {
            while (true) {
                try {
                    // Blocks until a slave connects
                    Socket slave = serverSocket.accept();
                    DataInputStream  in  = new DataInputStream(slave.getInputStream());
                    DataOutputStream out = new DataOutputStream(slave.getOutputStream());

                    slaves.add(slave);
                    inputs.add(in);
                    outputs.add(out);

                    System.out.println("Slave " + slaves.size() + " connected: " + slave.getInetAddress());
                } catch (IOException e) {
                    System.out.println("Acceptor error: " + e.getMessage());
                    break;
                }
            }
        });
        acceptorThread.setDaemon(true); // dies when main program exits
        acceptorThread.start();

        // --- Thread 2: Time Daemon ---
        // Wakes up every SYNC_INTERVAL seconds and runs Berkeley Algorithm
        Thread timeDaemon = new Thread(() -> {
            int round = 1;
            while (true) {
                try {
                    Thread.sleep(SYNC_INTERVAL * 1000);

                    if (slaves.isEmpty()) {
                        System.out.println("No slaves connected yet. Waiting...");
                        continue;
                    }

                    runBerkeleyAlgorithm(round);
                    round++;

                } catch (InterruptedException e) {
                    System.out.println("Daemon interrupted. Stopping.");
                    break;
                } catch (Exception e) {
                    System.out.println("Error during sync: " + e.getMessage());
                }
            }
        });
        // Daemon thread for running every 10 seconds
        timeDaemon.setDaemon(true);
        timeDaemon.start();

        System.out.println("Time Daemon is running...\n");
        Thread.currentThread().join(); // keeps main thread alive forever so daemon threads keep running
    }

    static void runBerkeleyAlgorithm(int round) throws Exception {

        // ---- PHASE 1: Master records its own time ----
        long masterTime = System.currentTimeMillis();
        System.out.println("\n--- SYNC ROUND #" + round + " ---");
        System.out.println("--- PHASE 1: Collecting Times ---");
        System.out.println("Master's current time : " + masterTime + " ms");

        // ---- PHASE 2: Ask each slave for its time ----
        List<Long>    timeDifferences = new ArrayList<>();
        List<Integer> failedIndices   = new ArrayList<>();

        for (int i = 0; i < slaves.size(); i++) {
            try {
                outputs.get(i).writeUTF("TIME_REQUEST");
                outputs.get(i).flush();

                long slaveTime = inputs.get(i).readLong();
                long diff = slaveTime - masterTime;
                timeDifferences.add(diff);

                System.out.println("Slave " + (i + 1) + " time      : " + slaveTime + " ms  |  Difference: " + diff + " ms");
            } catch (IOException e) {
                System.out.println("Slave " + (i + 1) + " disconnected. Removing.");
                failedIndices.add(i);
                timeDifferences.add(null);
            }
        }

        // Remove disconnected slaves (in reverse to avoid index shift)
        for (int i = failedIndices.size() - 1; i >= 0; i--) {
            int idx = failedIndices.get(i);
            slaves.remove(idx);
            inputs.remove(idx);
            outputs.remove(idx);
            timeDifferences.remove(idx);
        }

        if (timeDifferences.isEmpty()) {
            System.out.println("No active slaves. Skipping this round.");
            return;
        }

        // ---- PHASE 3: Calculate average difference ----
        System.out.println("\n--- PHASE 2: Calculating Average ---");
        long sumDiff = 0;
        for (long diff : timeDifferences) sumDiff += diff;
        // +1 for master itself (master diff = 0, but we count it in denominator)
        long avgDiff = sumDiff / (timeDifferences.size() + 1);

        System.out.println("Sum of differences : " + sumDiff + " ms");
        System.out.println("Average difference : " + avgDiff + " ms");

        // Master adjusts its own clock by avgDiff
        long newMasterTime = masterTime + avgDiff;
        System.out.println("Master's new time  : " + newMasterTime + " ms");

        // ---- PHASE 4: Send each slave its correction ----
        System.out.println("\n--- PHASE 3: Sending Corrections ---");
        for (int i = 0; i < timeDifferences.size(); i++) {
            try {
                // adjustment = avgDiff - slaveDiff
                // Example: avgDiff = -5ms, slaveDiff = +10ms => slave adjusts by -15ms
                long adjustment = avgDiff - timeDifferences.get(i);
                outputs.get(i).writeLong(adjustment);
                outputs.get(i).flush();
                System.out.println("Sent to Slave " + (i + 1) + ": adjustment = " + adjustment + " ms");
            } catch (IOException e) {
                System.out.println("Failed to send to Slave " + (i + 1));
            }
        }

        System.out.println("\nAll clocks are now synchronized! Next sync in " + SYNC_INTERVAL + " seconds.");
    }
}




















// import java.net.*;
// import java.io.*;
// import java.util.*;
// import java.text.SimpleDateFormat;

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
//  * Command: java Master
//  */
// public class Master {

//     static final int PORT          = 8000;  // Port to listen on
//     static final int SYNC_INTERVAL = 10;    // Sync every 10 seconds

//     // Shared list of connected slaves (used by both Daemon and Acceptor threads)
//     // synchronized list because multiple threads will access this list
//     static List<Socket>           slaves  = Collections.synchronizedList(new ArrayList<>()); // stores all slave connections
//     static List<DataInputStream>  inputs  = Collections.synchronizedList(new ArrayList<>()); // stores input streams
//     static List<DataOutputStream> outputs = Collections.synchronizedList(new ArrayList<>()); // stores output streams

//     // For printing readable timestamps
//     static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

//     public static void main(String[] args) throws Exception {

//         // Master starts listening for slaves
//         ServerSocket serverSocket = new ServerSocket(PORT);

//         System.out.println("╔══════════════════════════════════════════╗");
//         System.out.println("║   BERKELEY ALGORITHM - MASTER (DAEMON)   ║");
//         System.out.println("╚══════════════════════════════════════════╝");
//         System.out.println("Master IP   : " + InetAddress.getLocalHost().getHostAddress());
//         System.out.println("Port        : " + PORT);
//         System.out.println("Sync every  : " + SYNC_INTERVAL + " seconds");
//         System.out.println("Started at  : " + sdf.format(new Date()));
//         System.out.println("\nWaiting for slaves to connect...");
//         System.out.println("(Press Ctrl+C to stop the daemon)\n");

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

//                     System.out.println("[CONNECTED] Slave " + slaves.size()
//                             + " connected from: " + slave.getInetAddress().getHostAddress()
//                             + " at " + sdf.format(new Date()));

//                 } catch (IOException e) {
//                     System.out.println("[ERROR] Acceptor: " + e.getMessage());
//                     break;
//                 }
//             }
//         });
//         // making it a daemon thread
//         // Daemon thread for accepting slaves
//         acceptorThread.setDaemon(true); // dies when main program exits
//         // Start thread
//         acceptorThread.start();

//         // --- Thread 2: Time Daemon ---
//         // Wakes up every SYNC_INTERVAL seconds and runs Berkeley Algorithm
//         // Actual Berkeley Loop
//         Thread timeDaemon = new Thread(() -> {
//             int round = 1;
//             while (true) {
//                 try {
//                     // Wait before syncing
//                     Thread.sleep(SYNC_INTERVAL * 1000);

//                     // Skip Synchronisation
//                     if (slaves.isEmpty()) {
//                         System.out.println("[DAEMON] No slaves connected yet. Waiting...");
//                         continue;
//                     }

//                     // Run one full synchronization round of Berkeley Algorithm
//                     runSyncRound(round);
//                     round++;

//                 } catch (InterruptedException e) {
//                     System.out.println("[DAEMON] Interrupted. Stopping.");
//                     break;
//                 } catch (Exception e) {
//                     System.out.println("[DAEMON] Error during sync: " + e.getMessage());
//                 }
//             }
//         });
//         // Daemon thread for running every 10 seconds
//         timeDaemon.setDaemon(true);
//         timeDaemon.start();

//         // Keep main thread alive forever (daemon threads run in background)
//         System.out.println("[DAEMON] Time Daemon is running...\n");

//         // Prevents main thread from exiting
//         // Keeps daemon running forever
//         Thread.currentThread().join(); // waits forever
//     }

//     /**
//      * One full round of Berkeley Algorithm
//      */
//     static void runSyncRound(int round) throws Exception {

//         System.out.println("\n┌─────────────────────────────────────────┐");
//         System.out.println("│         SYNC ROUND #" + round + "  [" + sdf.format(new Date()) + "]       │");
//         System.out.println("└─────────────────────────────────────────┘");

//         // ---- PHASE 1: Record master's own time ----
//         long masterTime = System.currentTimeMillis();
//         System.out.println("[MASTER] My current time: " + masterTime
//                 + " ms  (" + sdf.format(new Date(masterTime)) + ")");

//         // ---- PHASE 2: Ask each slave for its time ----
//         List<Long> differences = new ArrayList<>();
//         List<Integer> failedIndices = new ArrayList<>();  // track disconnected slaves

//         System.out.println("\n[PHASE 1] Requesting times from " + slaves.size() + " slave(s)...");

//         for (int i = 0; i < slaves.size(); i++) {
//             try {
//                 // Send time request
//                 outputs.get(i).writeUTF("TIME_REQUEST");
//                 outputs.get(i).flush();

//                 // Receive slave time
//                 long slaveTime = inputs.get(i).readLong();
//                 long diff = slaveTime - masterTime;
//                 differences.add(diff);

//                 System.out.println("  Slave " + (i + 1)
//                         + " time: " + slaveTime + " ms"
//                         + "  (" + sdf.format(new Date(slaveTime)) + ")"
//                         + "  → diff: " + (diff >= 0 ? "+" : "") + diff + " ms");

//             } catch (IOException e) {
//                 System.out.println("  Slave " + (i + 1) + " disconnected. Removing.");
//                 failedIndices.add(i);
//                 differences.add(null); // placeholder
//             }
//         }

//         // Remove disconnected slaves (in reverse to avoid index shift)
//         for (int i = failedIndices.size() - 1; i >= 0; i--) {
//             int idx = failedIndices.get(i);
//             slaves.remove(idx);
//             inputs.remove(idx);
//             outputs.remove(idx);
//             differences.remove(idx);
//         }

//         if (differences.isEmpty()) {
//             System.out.println("[DAEMON] No active slaves. Skipping this round.");
//             return;
//         }

//         // ---- PHASE 3: Calculate average difference ----
//         System.out.println("\n[PHASE 2] Calculating average...");

//         long sumDiff = 0;
//         for (long d : differences) sumDiff += d;

//         // Divide by (slaves + 1) because master's own diff = 0 is also included
//         long avgDiff = sumDiff / (differences.size() + 1);

//         System.out.println("  Sum of differences : " + sumDiff + " ms");
//         System.out.println("  Number of nodes    : " + (differences.size() + 1) + " (slaves + master)");
//         System.out.println("  Average difference : " + avgDiff + " ms");

//         // Master adjusts itself
//         long newMasterTime = masterTime + avgDiff;
//         System.out.println("  Master adjusts by  : " + avgDiff + " ms");
//         System.out.println("  Master's new time  : " + newMasterTime
//                 + " ms  (" + sdf.format(new Date(newMasterTime)) + ")");

//         // ---- PHASE 4: Send corrections to slaves ----
//         System.out.println("\n[PHASE 3] Sending corrections...");

//         for (int i = 0; i < differences.size(); i++) {
//             try {
//                 long adjustment = avgDiff - differences.get(i);
//                 outputs.get(i).writeLong(adjustment);
//                 outputs.get(i).flush();

//                 System.out.println("  Slave " + (i + 1)
//                         + " → adjustment: " + (adjustment >= 0 ? "+" : "") + adjustment + " ms"
//                         + "  (new time: " + (System.currentTimeMillis() + adjustment) + " ms)");

//             } catch (IOException e) {
//                 System.out.println("  Failed to send to Slave " + (i + 1));
//             }
//         }

//         System.out.println("\n✓ Round #" + round + " complete. Next sync in " + SYNC_INTERVAL + " seconds.");
//     }
// }