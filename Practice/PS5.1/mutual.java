import java.util.Scanner;


public class mutual {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        // number of process input
        System.out.print("Enter the number of process: ");
        int n = sc.nextInt();

        // display the ring
        for(int i = 0; i < n; i++){
            System.out.print(i + " --> ");
        }
        System.out.println("0");

        int token = 0;

        int choice;

        do{
            System.out.println("Current token at the process: " + token);

            System.out.print("Enter the sender process number: ");
            int sender = sc.nextInt();

            System.out.print("Enter the reciever process number: ");
            int reciever = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter data: ");
            String data = sc.nextLine();

            if(sender < 0 || sender >= n || reciever < 0 || reciever >= n){
                System.out.println("Invalid process number");
            }
            else{
                System.out.println("Passing the token to sender...");

                while(token != sender){
                    System.out.print(token + " --> ");
                    token = (token + 1) % n;
                }
                System.out.println(sender);

                System.out.println();

                System.out.println("Token acquired by process " + token);

                System.out.println("Process " + token + " enters CRITICAL SECTION");

                System.out.println("Sending data: " + data);

                int temp = sender;
                while(temp != reciever){
                    int next = (temp + 1) % n;
                    System.out.println("Data forwarded from Process " + temp + " -> " + next);
                    temp = next;
                }
                System.out.println("Sent data: " + data);

                token = (sender + 1) % n;

                System.out.println("Token passed to Process: " + token);



            }


            System.out.println("1. Continue \n 2. Exit the Program");
            choice = sc.nextInt();

        }while(choice == 1);
    }
    
}
