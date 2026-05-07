import java.util.Scanner;

/*
1. input process
2. show ring
3. token initially at 0
4. choice --> do while loop
5. show which process has input, sender + receiver input
6. sc.nextLine() --> clears input buffer
7. data input
8. if --> validate process
9. else -->  token movement until sender gets it 
10. sender enters critical section
11. forward data till temp != receiver
12. receiver gets data --> exit critical section --> pass token to next process
*/
public class mutual2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter Number of Processes: ");
        int n = sc.nextInt();

        // Display ring structure
        System.out.println("\nRing Structure:");

        for (int i = 0; i < n; i++) {
            System.out.print(i + " -> ");
        }

        System.out.println("0");

        // Token initially at process 0
        int token = 0;

        int choice;

        do {

            // Show which process currently has token
            System.out.println("\nCurrent Token at Process: " + token);

            // Sender input (process wanting to send)
            System.out.print("Enter Sender Process: ");
            int sender = sc.nextInt();

            // Receiver input (destination process)
            System.out.print("Enter Receiver Process: ");
            int receiver = sc.nextInt();

            sc.nextLine(); // clear input buffer 
            //This removes leftover newline.
            // Otherwise next string input may get skipped.

            // Data input
            System.out.print("Enter Data: ");
            String data = sc.nextLine();

            // Validate processes
            if (sender < 0 || sender >= n || receiver < 0 || receiver >= n) {

                System.out.println("Invalid Process Number!");

            } else {

                // Token movement
                System.out.println("\nToken Passing:");

                while (token != sender) { //Token keeps moving until sender gets token.

                    System.out.print(token + " -> ");

                    token = (token + 1) % n;   //Moves token circularly.
                }

                System.out.println(sender);  //show token reach sender

                // Critical Section ---> part of code where shared resource is used.
                //only token holder can enter this CS --> ensure mutual exclusion
                //sender now accesses shared resource.
                System.out.println("\nToken acquired by Process " + sender);

                System.out.println("Process " + sender + " ENTERS Critical Section");

                // Sending data
                System.out.println("\nSending Data: " + data);

                // Data forwarding through ring until it reaches receiver
                int temp = sender;

                while (temp != receiver) {

                    int next = (temp + 1) % n;

                    System.out.println(
                            "Data forwarded from Process "
                                    + temp
                                    + " to Process "
                                    + next);

                    temp = next;
                }

                // Receiver gets data
                System.out.println("\nReceiver " + receiver
                        + " received data: " + data);

                // Exit critical section
                System.out.println("Process " + sender
                        + " EXITS Critical Section");

                // Pass token to next process in ring after sender
                token = (sender + 1) % n;

                System.out.println("Token passed to Process: " + token);
            }

            // Continue option
            System.out.print("\nEnter 1 to Continue, 0 to Stop: ");
            choice = sc.nextInt();

        } while (choice == 1);

        // sc.close();
    }
} 
