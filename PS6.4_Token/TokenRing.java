import java.util.Scanner;

public class TokenRing {

    static int n;
    static int[] process;
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

        // Continuous loop
        while (true) {

            System.out.println("\n1. Crash & Election");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            if (choice == 1) {

                // Crash process
                System.out.print("Enter process to crash: ");
                int crash = sc.nextInt();

                for (int i = 0; i < n; i++) {
                    if (process[i] == crash) {
                        active[i] = false;
                        System.out.println("Process " + crash + " is DOWN");
                    }
                }

                // Start election
                System.out.print("Enter process to start election: ");
                int initiator = sc.nextInt();

                // Check active
                boolean alive = false;
                int startIndex = -1;

                for (int i = 0; i < n; i++) {
                    if (process[i] == initiator) {
                        startIndex = i;
                        if (active[i]){
                            alive = true;
                        }
                    }
                }

                if (!alive) {
                    System.out.println("Process is DOWN. Cannot start election.");
                    continue;
                }

                election(startIndex);

            } else if (choice == 2) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }

        sc.close();
    }

    static void election(int start) {

        int i = start;
        int max = -1;

        System.out.println("\nElection started...");

        do {
            if (active[i]) {
                System.out.println("Process " + process[i] + " sends token");
                if (process[i] > max) {
                    max = process[i];
                }
            }

            i = (i + 1) % n;

        } while (i != start);

        coordinator = max;
        System.out.println("Process " + coordinator + " becomes COORDINATOR");

        // Broadcast
        for (int j = 0; j < n; j++) {
            if (process[j] != coordinator && active[j]) {
                System.out.println("Process " + coordinator + " -> " + process[j] + " : COORDINATOR");
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