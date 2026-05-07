import java.net.*;
import java.io.*;

/**
 * BERKELEY ALGORITHM WITH TIME DAEMON - SLAVE
 * =============================================
 * The Slave also runs a daemon-like listener loop.
 * It stays connected to the Master and responds to
 * every sync request that the Master's daemon sends.
 *
 * Flow:
 * 1. Slave connects to Master
 * 2. Waits for "TIME_REQUEST" from Master
 * 3. Sends its current time to Master
 * 4. Receives adjustment value from Master
 * 5. Applies adjustment and prints the result
 * 6. Goes back to step 2 (repeats forever)
 *
 * Run on: Machine 2 (Slave machine)
 * Command: java SlaveDaemon <master_ip>
 */
public class SlaveDaemon {

    static final int PORT = 8000;

    public static void main(String[] args) throws Exception {

        String masterIP = (args.length > 0) ? args[0] : "localhost";

        System.out.println("=== BERKELEY ALGORITHM - SLAVE (DAEMON) ===");
        System.out.println("Connecting to Master at " + masterIP + ":" + PORT + " ...");

        // Step 1: Connect to master
        Socket socket = new Socket(masterIP, PORT);
        System.out.println("Connected to Master successfully!\n");

        DataInputStream  in  = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        System.out.println("Waiting for sync requests from Master...");
        System.out.println("(Press Ctrl+C to stop)\n");

        int round = 1;

        // Keep listening for sync requests — this is the "daemon" behaviour on slave side
        while (true) {
            try {
                // Step 2: Wait for Master's time request
                String request = in.readUTF();   // blocks here until master sends "TIME_REQUEST"
                System.out.println("\n--- SYNC ROUND #" + round + " ---");
                System.out.println("Received from Master: " + request);

                // Step 3: Send current time to Master
                long myCurrentTime = System.currentTimeMillis();
                out.writeLong(myCurrentTime);
                out.flush();
                System.out.println("Sent my time to Master: " + myCurrentTime + " ms");

                // Step 4: Receive adjustment from Master
                long adjustment = in.readLong();
                System.out.println("Received adjustment from Master: " + adjustment + " ms");

                // Step 5: Apply the adjustment
                long synchronizedTime = myCurrentTime + adjustment;
                System.out.println("My old time             : " + myCurrentTime + " ms");
                System.out.println("Adjustment to apply     : " + adjustment + " ms");
                System.out.println("My new synchronized time: " + synchronizedTime + " ms");

                if (adjustment > 0) {
                    System.out.println("Action: My clock was BEHIND. Moving clock FORWARD by " + adjustment + " ms.");
                } else if (adjustment < 0) {
                    System.out.println("Action: My clock was AHEAD. Moving clock BACKWARD by " + Math.abs(adjustment) + " ms.");
                } else {
                    System.out.println("Action: My clock was already in sync. No adjustment needed.");
                }

                round++;

            } catch (EOFException | SocketException e) {
                // Master disconnected
                System.out.println("\nMaster disconnected. Slave daemon stopping.");
                break;
            }
        }

        socket.close();
        System.out.println("Synchronization complete. Slave done.");
    }
}


















// import java.net.*;
// import java.io.*;
// import java.util.*;
// import java.text.SimpleDateFormat;

// /**
//  * BERKELEY ALGORITHM WITH TIME DAEMON - SLAVE
//  * =============================================
//  * The Slave also runs a daemon-like listener thread.
//  * It stays connected to the Master and responds to
//  * every sync request that the Master's daemon sends.
//  *
//  * Flow:
//  * 1. Slave connects to Master
//  * 2. Slave's listener thread waits for "TIME_REQUEST"
//  * 3. On request: sends current time to Master
//  * 4. Receives adjustment value from Master
//  * 5. Applies adjustment and prints the result
//  * 6. Goes back to waiting for next request (repeats forever)
//  *
//  * Run on: Machine 2 (Slave machine)
//  * Command: java Slave <master_ip>
//  * Example: java Slave 192.168.1.10
//  */
// public class Slave {

//     static final int PORT = 8000;

//     // Simulated clock offset (in ms) — represents this slave's clock drift
//     // In a real system this would be the actual system clock
//     // Here we add a small random drift so output is more interesting
//     static long clockOffset = 0;

//     static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

//     public static void main(String[] args) throws Exception {

//         // Get master IP from command line, default to localhost
//         String masterIP = (args.length > 0) ? args[0] : "localhost";

//         // Optional: simulate a clock drift for demo purposes
//         // This makes the sync more visible when testing on 1 machine
//         Random rand = new Random();
//         clockOffset = rand.nextInt(2000) - 1000; // random drift between -1000ms to +1000ms

//         System.out.println("╔══════════════════════════════════════════╗");
//         System.out.println("║   BERKELEY ALGORITHM - SLAVE (DAEMON)    ║");
//         System.out.println("╚══════════════════════════════════════════╝");
//         System.out.println("Master IP      : " + masterIP);
//         System.out.println("Port           : " + PORT);
//         System.out.println("Simulated drift: " + clockOffset + " ms");
//         System.out.println("Started at     : " + sdf.format(new Date()));
//         System.out.println("\nConnecting to Master...");

//         // Connect to master
//         Socket socket = new Socket(masterIP, PORT);
//         DataInputStream  in  = new DataInputStream(socket.getInputStream());
//         DataOutputStream out = new DataOutputStream(socket.getOutputStream());

//         System.out.println("✓ Connected to Master at "
//                 + masterIP + ":" + PORT
//                 + " on " + sdf.format(new Date()));
//         System.out.println("\n[SLAVE DAEMON] Waiting for sync requests from Master...");
//         System.out.println("(Press Ctrl+C to stop)\n");

//         int round = 1;

//         // Keep listening for sync requests from Master daemon
//         // This loop runs forever — that's the "daemon" behavior on slave side
//         while (true) {
//             try {
//                 // ---- Wait for Master's time request ----
//                 String request = in.readUTF(); // blocks here until master sends something

//                 if (!request.equals("TIME_REQUEST")) {
//                     System.out.println("[SLAVE] Unknown request: " + request);
//                     continue;
//                 }

//                 System.out.println("┌──────────────────────────────────────────┐");
//                 System.out.println("│        SYNC ROUND #" + round + "  [" + sdf.format(new Date()) + "]        │");
//                 System.out.println("└──────────────────────────────────────────┘");

//                 // ---- Send current (possibly drifted) time to Master ----
//                 long myTime = System.currentTimeMillis() + clockOffset;
//                 out.writeLong(myTime);
//                 out.flush();

//                 System.out.println("[PHASE 1] Sent my time to Master:");
//                 System.out.println("  Raw system time  : " + System.currentTimeMillis() + " ms");
//                 System.out.println("  My clock (drifted): " + myTime
//                         + " ms  (" + sdf.format(new Date(myTime)) + ")");
//                 System.out.println("  Clock drift applied: " + clockOffset + " ms");

//                 // ---- Receive adjustment from Master ----
//                 long adjustment = in.readLong();

//                 System.out.println("\n[PHASE 2] Received correction from Master:");
//                 System.out.println("  Adjustment       : " + (adjustment >= 0 ? "+" : "") + adjustment + " ms");

//                 // ---- Apply adjustment ----
//                 long oldTime    = myTime;
//                 long newTime    = myTime + adjustment;
//                 clockOffset    += adjustment; // update our running offset

//                 System.out.println("\n[PHASE 3] Applying correction:");
//                 System.out.println("  Old time         : " + oldTime
//                         + " ms  (" + sdf.format(new Date(oldTime)) + ")");
//                 System.out.println("  New synced time  : " + newTime
//                         + " ms  (" + sdf.format(new Date(newTime)) + ")");

//                 if (adjustment > 0) {
//                     System.out.println("  Action: Clock was BEHIND → moved FORWARD by " + adjustment + " ms");
//                 } else if (adjustment < 0) {
//                     System.out.println("  Action: Clock was AHEAD  → moved BACKWARD by " + Math.abs(adjustment) + " ms");
//                 } else {
//                     System.out.println("  Action: Clock already in sync. No change needed.");
//                 }

//                 System.out.println("✓ Round #" + round + " done. Waiting for next sync...\n");
//                 round++;

//             } catch (EOFException | SocketException e) {
//                 // Master disconnected
//                 System.out.println("\n[SLAVE] Master disconnected. Slave daemon stopping.");
//                 break;
//             } catch (IOException e) {
//                 System.out.println("\n[SLAVE] Connection error: " + e.getMessage());
//                 break;
//             }
//         }

//         socket.close();
//         System.out.println("[SLAVE] Done.");
//     }
// }