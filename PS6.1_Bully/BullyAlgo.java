import java.util.Scanner;

public class BullyAlgo {

    static int n; 
    static int[] process; // stores process ID
    static boolean[] active;
    static int coordinator;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        n = sc.nextInt();

        process = new int[n];
        active = new boolean[n];

        System.out.println("Enter process IDs:");
        for (int i = 0; i < n; i++) {
            process[i] = sc.nextInt();
            active[i] = true;
        }

        coordinator = getMax();
        System.out.println("Initial Coordinator: " + coordinator);

        // Continuous execution
        while (true) {

            System.out.println("\n1. Crash & Election");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            if (choice == 1) {

                // Crash process
                System.out.print("Enter process to crash: ");
                int crash = sc.nextInt();

                boolean found1 = false;
                for (int i = 0; i < n; i++) {
                    if (process[i] == crash) {
                        found1 = true;

                        if(!active[i]){
                            System.out.println("Process " + i + " is already DOWN");
                        }
                        else{
                            active[i] = false;
                            System.out.println("Process " + crash + " is DOWN");
                        }
                    }
                }

                if (!found1){
                    System.out.println("Invalid process ID");
                    continue;
                }

                // Start election
                System.out.print("Enter process to start election: ");
                int initiator = sc.nextInt();

                // Check if initiator is active
                boolean alive = false;
                boolean found2 = false;
                for (int i = 0; i < n; i++) {
                    if (process[i] == initiator) {
                        found2 = true;

                        if(active[i]){
                            alive = true;
                        }
                    }
                }


                if (!alive) {
                    System.out.println("Process is DOWN. Cannot start election.");
                    continue;
                }

                election(initiator);

            } else if (choice == 2) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
        sc.close();
    }

    static void election(int initiator) {

        boolean higher = false;

        // Send ELECTION
        for (int i = 0; i < n; i++) {
            if (process[i] > initiator && active[i]) {
                System.out.println("Process " + initiator + " -> " + process[i] + " : ELECTION");
                higher = true;
            }
        }

        if (!higher) {
            coordinator = initiator;
            System.out.println("Process " + initiator + " becomes COORDINATOR");
        } else {

            // OK messages
            for (int i = 0; i < n; i++) {
                if (process[i] > initiator && active[i]) {
                    System.out.println("Process " + process[i] + " -> " + initiator + " : OK");
                }
            }

            coordinator = getMax();
            System.out.println("Process " + coordinator + " becomes COORDINATOR");
        }

        // Broadcast
        for (int i = 0; i < n; i++) {
            if (process[i] != coordinator && active[i]) {
                System.out.println("Process " + coordinator + " -> " + process[i] + " : COORDINATOR");
            }
        }
    }

    static int getMax() {
        int max = -1;
        for (int i = 0; i < n; i++) {
            if (active[i] && process[i] > max) {
                max = process[i];
            }
        }
        return max;
    }
}