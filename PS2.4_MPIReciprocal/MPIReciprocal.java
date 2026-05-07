import mpi.*;
/**
 * Root array:      [  1    2    3    4  ]
 *                    ↓    ↓    ↓    ↓
 *                   P0   P1   P2   P3     ← Scatter
 *
 * Each process:   1/1  1/2  1/3  1/4     ← Reciprocal
 *
 *                    ↓    ↓    ↓    ↓
 *                         Gather
 *
 * Resultant:    [1.0  0.5  0.333  0.25]  ← Displayed at Root
 *
 * Flow: Array → Scatter → Reciprocal (1/x) → Gather → Display
 */

public class MPIReciprocal {
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

        MPI.COMM_WORLD.Scatter(send, 0, chunkSize, MPI.INT, recv, 0, chunkSize, MPI.INT, 0);

        double[] reciprocals = new double[recv.length];
        for(int i = 0; i < recv.length; i++){
            reciprocals[i] = (double)1.0 / recv[i];
        }

        double[] finalReciprocal = new double[n];
        // MPI.COMM_WORLD  .Gather(reciprocals, 0, chunkSize, MPI.DOUBLE, finalReciprocal, size, n, null, chunkSize);
        MPI.COMM_WORLD.Gather(reciprocals, 0, chunkSize, MPI.DOUBLE, finalReciprocal, 0, chunkSize, MPI.DOUBLE, 0);

        if(rank == 0){
            System.out.print("Reciprocal List : ");
            for(int i = 0; i < n; i++){
                System.out.print(finalReciprocal[i] + " ");
            }
            System.out.println();
        }
        MPI.Finalize();

    }
}
