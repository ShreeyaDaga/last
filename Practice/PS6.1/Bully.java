import java.util.Scanner;

public class Bully {
    static int[] process;
    static boolean[] active;
    static int n;
    static int coordinator;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of processes: ");
        n = sc.nextInt();

        process = new int[n];
        active = new boolean[n];

        System.out.println("Enter Process IDs:");
        for(int i = 0; i < n; i++){
            process[i] = sc.nextInt();
            active[i] = true;
        }

        coordinator = getMax();
        System.out.println("Process " + coordinator + " is the COORDINATOR");

        while(true){
            System.out.println("Enter Choice: ");
            System.out.println("1. Crash and Election");
            System.out.println("2. Exit");
            int choice = sc.nextInt();

            if(choice == 1){
                System.out.println("Enter the Process ID of the process you want to crash: ");
                int crash = sc.nextInt();

                boolean found = false;
                for(int i = 0; i < n; i++){
                    if(process[i] == crash){
                        found = true;
                        if(active[i]){
                            active[i] = false;
                            System.out.println("Process " + process[i] + " is DOWN");                            
                        }
                        else{
                            System.out.println("Process " + process[i] + " is already DOWN");
                        }
                    }
                }

                if(!found){
                    System.out.println("Process " + crash + " is an invalid Process ID");
                }


                System.out.println("Enter Process ID to start election");
                int initiator = sc.nextInt();

                boolean found1 = false;
                boolean alive = false;
                for(int i = 0; i < n; i++){
                    if(process[i] == initiator){
                        found1 = true;
                        if(active[i]){
                            alive = true;
                        }
                    }
                }

                if(!found1){
                    System.out.println("Invalid process ID to start election");
                }
                if(!alive){
                    System.out.println("Process " + initiator + " is already DOWN");
                }

                election(initiator);
              
            }
            if(choice == 2){
                System.out.println("Exiting the program");
                break;
            }
            else{
                System.out.println("Invalid Choice");
            }
        }
        sc.close();
    }

    static void election(int initiator){
        boolean higher = false;

        for(int i = 0; i < n; i++){
            if(process[i] > initiator && active[i]){
                System.out.println("Process " + initiator + " --> " + process[i] + " : ELECTION");
                higher = true;
            }
        }

        if(!higher){
            System.out.println("Process " + initiator + " is the COORDINATOR");
        }
        else{
            for(int i = 0; i < n; i++){
                if(process[i] > initiator && active[i]){
                    System.out.println("Process " + process[i] + " --> " + initiator + " : OK");
                }
            }
        }

        coordinator = getMax();
        System.out.println("Process " + coordinator + " is the COORDINATOR");

        //boradcast
        for(int i = 0; i < n; i++){
                if(process[i] != coordinator && active[i]){
                    System.out.println("Process " + process[i] + " --> " + initiator + " : COORDINATOR");
                }
            }

    }

    static int getMax(){
        int max = -1;
        for(int i = 0; i < n; i++){
            if(active[i] && (process[i] > max)){
                max = process[i];
            }
        }
        return max;
    }
    
}
