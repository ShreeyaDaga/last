import mpi.*;

public class MPISum {
    public static void main(String[] args) throws Exception {

        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        // ALL processes must initialize array (not just root)
        // Non-root processes won't use the data, but MPJ needs a non-null buffer
        int[] array = new int[size];
        int[] subArray = new int[1];
        int[] localSum = new int[1];
        int[] totalSum = new int[1];

        // Only root fills the array with actual data
        if (rank == 0) {
            System.out.println("\n[Root] Array created with " + size + " elements:");
            for (int i = 0; i < size; i++) {
                array[i] = (i + 1) * 10;
                System.out.print("  array[" + i + "] = " + array[i]);
            }
            System.out.println("\n[Root] Scattering one element to each process...\n");
        }

        // Now Scatter works because array is non-null on ALL processes
        MPI.COMM_WORLD.Scatter(
                array, 0, 1, MPI.INT,
                subArray, 0, 1, MPI.INT,
                0
        );

        localSum[0] = subArray[0];
        System.out.println("  [Process " + rank + "] Received: " + subArray[0]
                + "  -->  Local sum = " + localSum[0]);

        MPI.COMM_WORLD.Reduce(
                localSum, 0, totalSum, 0,
                1, MPI.INT,
                MPI.SUM,
                0
        );

        if (rank == 0) {
            System.out.println("\n[Root] Total sum of all elements = " + totalSum[0]);
        }

        MPI.Finalize();
    }
}