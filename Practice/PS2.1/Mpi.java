package MPI;
import mpi.*;

public class Mpi {

    public static void main(String [] args) throws Exception{

        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int n = 8;
        int chunkSize = n/size;

        int[] send = null;
        int[] recv = new int[chunkSize];

        if(rank == 0){

            send = new int[]{1,2,3,4,5,6,7,8};
            System.out.println("Root array created with " + n + "elements");
            for(int i=0; i<n; i++){
                System.out.println("array: [" + i + "] = " + send[i]);
            }
        }else{
            send = new int[n];
        }

        MPI.COMM_WORLD.Scatter(send, 0, chunkSize, MPI.INT, recv, 0, chunkSize, MPI.INT, 0);

        double localSum = 0;
        double localAvg = 0;

        for(int i=0; i<recv.length; i++){
            localSum+=recv[i];
        }
        localAvg = localSum/recv.length;

        for(int i=0; i<size; i++){
            MPI.COMM_WORLD.Barrier();
            System.out.println("Process [" + i+ "] received: ");
            for(int j=0; j<recv.length; j++){
                System.out.println(j + ", ");
            }
            System.out.println("Local average is " + localAvg);
        }
        
        double[] gather = new double[size];
        double[] localAvgArr = {localAvg};

        MPI.COMM_WORLD.Gather(localAvgArr, 0, 1, MPI.DOUBLE, gather, 0, 1, MPI.DOUBLE, 0);

        System.out.println("");

    }


    

}