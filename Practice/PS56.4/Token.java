import java.util.Scanner;

public class Token {
    static int[] process;
    static boolean[] active;
    static int n;
    static int coordinator;
    
    public static void main(String[] args){
        System.out.print("Enter the total number of processes: ");
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();

        process = new int[n];
        active = new boolean[n];

        System.out.println("Enter the Process ID of all the process: ");
        for(int i = 0; i < n; i++){
            process[i] = sc.nextInt();
            active[i] = true;
        }

        coordinator = getMax();
        System.out.println("Process " + coordinator + " is the COORDINATOR");

        while (true) {
            System.out.println();
            System.out.println("\nEnter choice: ");
            System.out.println("1. Crash and Election");
            System.out.println("2. Exit");
            int choice = sc.nextInt();

            if(choice == 1){
                System.out.print("Enter the Process ID of the process you want to crash: ");
                int crash = sc.nextInt();

                boolean found = false;
                for(int i = 0; i < n; i++){
                    if(process[i] == crash){
                        found = true;
                        if(active[i]){
                            active[i] = false;
                            System.out.println("Process " + process[i] + " is DOWN now");
                        }
                        else{
                            System.out.println("Process " + process[i] + " is already DOWN");
                        }
                    }
                }

                if(!found){
                    System.out.println("Invalid Process ID!");
                    System.out.println("Process ID not found");
                    System.out.println();
                }



                boolean found1 = false;
                boolean alive = false;

                System.out.print("Enter the Process ID to start Election: ");
                int initiator = sc.nextInt();

                int startIndex = 0; 
                for(int i = 0; i < n; i++){
                    if(process[i] == initiator){
                        startIndex = i;
                        found1 = true;
                        if(active[i]){
                            alive = true;
                        }
                    }
                }

                if(!found1){
                    System.out.println("Invalid Process ID");
                }
                if(!alive){
                    System.out.println("Process " + initiator + " is DOWN");
                }

                election(startIndex);


            }
            else if(choice == 2){
                System.out.println("Exiting the program");
                break;
            }
            else{
                System.out.println("Invalid choice number");
            }
        }


    }

    static int getMax(){
        int max = -1;
        for(int i = 0; i < n; i++){
            if(process[i] > max){
                max = process[i];
            }
        }
        return max;
    }

    static void election(int start){
        int i = start;
        int max = -1;

        System.out.println("Election has started...");

        do{
            if(active[i]){
                System.out.println("Process " + process[i] + " sends tokens");
                if(process[i] > max){
                    max = process[i];
                }
            }
            i = (i+1) % n;
        }while(i != start);

        coordinator = max;
        System.out.println("Process " + coordinator + " is the COORDINATOR");
        for(int j = 0; j < n; j++){
            if(process[j] != coordinator && active[j]){
                System.out.println("Process " + coordinator + " --> " + process[j] + " : COORDINATOR");
            }
        }

    }
}
