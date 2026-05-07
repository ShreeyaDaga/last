import mpi.MPI;

public class MPIMultiply {
    public static void main(String[] args) throws Exception{
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int n = 8;
        int chunkSize = n / size;

        int[] send = null;
        int[] recv = new int[chunkSize];

        if (rank == 0){
            send = new int[]{1,2,3,4,5,6,7,8};

            System.out.println("[Root] Array created with " + n + " elements");
            for(int i = 0; i < n; i++){
                System.out.print("Array[" + i + "] = " + send[i] + "   ");
            }

            System.out.println("\n\n\n[Root] Scattering " + chunkSize + " elements(s) to each process...\n\n");
            System.out.println("");
        }
        else{
            send = new int[n];
        }

        MPI.COMM_WORLD.Scatter(send,0,chunkSize, MPI.INT, recv, 0, chunkSize, MPI.INT, 0);

        int localProd = 1;
        for(int i = 0; i < recv.length; i++){
            localProd *= recv[i]; 
        }


        for(int i = 0; i < size; i++){
            MPI.COMM_WORLD.Barrier();
            if (rank == i){
                System.out.print("[Process " + i + "] Recieved:");
                for(int j = 0; j < chunkSize; j++){
                    System.out.print(recv[j] + " ");
                }
                System.out.println(" --> Local Product = " + localProd);
            }
        }

        int[] localProdArr = {localProd};
        int[] totalProd = new int[1];

        MPI.COMM_WORLD.Reduce(localProdArr, 0, totalProd, 0, 1, MPI.INT, MPI.PROD, 0);

        if (rank == 0){
            System.out.println("[Root] Total product of elements = " + totalProd[0]);
        }

        MPI.Finalize();
    }
}
