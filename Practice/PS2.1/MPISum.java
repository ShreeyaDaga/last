import mpi.MPI;

public class MPISum{
    public static void main(String[] args) throws Exception{
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int n = 8;
        int chunkSize = n / size;

        int[] send = null;
        int[] recv = new int[chunkSize];

        if(rank == 0){
            send = new int[]{1,2,3,4,5,6,7,8};
            for(int i = 0; i < n; i++){
                System.out.println("Array [" + i + "] = " + send[i]);
            }
        }
        else{
            send = new int[n]; // dummy array
        }

        MPI.COMM_WORLD.Scatter(send, 0, chunkSize, MPI.INT, recv, 0, chunkSize, MPI.INT, 0);

        int localSum = 0;
        for(int i = 0; i < recv.length; i++){
            localSum += recv[i];
        }

        for(int i = 0; i < size; i++){
            MPI.COMM_WORLD.Barrier();
            if(rank == i){
                System.out.print("Process [" + rank + "] Recieved: ");
                for(int j = 0; j < chunkSize; j++){
                    System.out.print(recv[j] + " ");
                }
                System.out.println(" Local Sum = " + localSum);
            }
        }

        int[] localSumArr = {localSum};
        int[] totalSum = new int[1];

        MPI.COMM_WORLD.Reduce(localSumArr, 0, totalSum, 0, 1, MPI.INT, MPI.SUM, 0);

        if(rank == 0){
            System.out.println("Total Sum of elements: " + totalSum[0]);
        }

        MPI.Finalize();
    }
}