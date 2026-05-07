import mpi.*;

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

            System.out.println("[ROOT] Array created with " + n + " elements");
            for(int i = 0; i < n; i++){
                System.out.println("Array [" + i + "] = " + send[i] + "   ");
            }

            System.out.println("[ROOT] Scattering " + chunkSize + " element(s) to each process...");
        }
        else{
            send = new int[n];
        }

        MPI.COMM_WORLD.Scatter(send, 0, chunkSize, MPI.INT, recv, 0, chunkSize, MPI.INT, 0);

        double localAvg = 0;
        int localSum = 0;
        for(int i = 0; i < recv.length; i++){
            localSum += recv[i];
        }
        localAvg = (double) localSum / recv.length;

        for(int i = 0; i < size; i++){
            MPI.COMM_WORLD.Barrier();
            if (rank == i){
                System.out.print("Process [" + i + "] Recieved: ");
                for(int j = 0; j < chunkSize; j++){
                    System.out.print(recv[j] + " ");
                }
                System.out.println(" --> Local Average = " + localAvg);
            }
        }

        double[] gathered = new double[size];
        double[] localAvgArr = new double[]{localAvg};


        MPI.COMM_WORLD.Gather(localAvgArr, 0, 1, MPI.DOUBLE, gathered, 0, 1, MPI.DOUBLE, 0);
        // Collects data from all processes and stores it at the root
        // many → one (opposite of Scatter)
        /**
         * localAvgArr - send buffer - Each process sends its local value
         * 0           - send offset - 
         * 1           - send count  - Number of elements each process sends
         * MPI.DOUBLE  - sendtype    - Datatype of element in send, MPI compatible data type
         * gathered    - recv buffer - Array where root collects all values
         * 0           - recv offset - starting index in recieve buffer
         * 1           - recv count  - Number of elements received from each process
         * MPI.DOUBLE  - recv type   - Datatype of element in recv
         * 0           - root        - Rank of root process
         */

        // root computes final average
        if (rank == 0){
            double finalSum = 0;
            for(int i = 0; i < gathered.length; i++){
                finalSum += gathered[i];
            }

            double finalAvg = (double)finalSum / size;

            System.out.println("Final Average: " + finalAvg);
        }
        

        MPI.Finalize();
    }    
}
