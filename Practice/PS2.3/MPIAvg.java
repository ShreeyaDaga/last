import mpi.MPI;

public class MPIAvg {
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
            send = new int[n];
        }

        MPI.COMM_WORLD.Scatter(send,0,chunkSize,MPI.INT, recv, 0, chunkSize, MPI.INT,0);

        double localAvg = 0;
        int localSum = 0;
        for(int i = 0; i < recv.length; i++){
            localSum += recv[i];
        }
        localAvg = (double) localSum / recv.length;

        for(int i = 0; i < size; i++){
            MPI.COMM_WORLD.Barrier();
            if(rank == i){
                
                System.out.print("Process [" + i + "] Recived: ");
                for(int j = 0; j < chunkSize; j++){
                    System.out.print(recv[j] + " ");
                }
                System.out.println("Local Average = " + localAvg);
            }
        }

        double[] localAvgArr = {localAvg};
        double[] gathered = new double[size];

        MPI.COMM_WORLD.Gather(localAvgArr, 0, 1, MPI.DOUBLE, gathered, 0 , 1, MPI.DOUBLE, 0);

        if(rank == 0){
            double finalSum = 0;
            for(int i = 0; i < gathered.length; i++){
                finalSum += gathered[i];
            }

            double finalAvg = (double) finalSum / size;

            System.out.println("Total Average of all the elements: " + finalAvg);
        }
        
        MPI.Finalize();

    }
}
