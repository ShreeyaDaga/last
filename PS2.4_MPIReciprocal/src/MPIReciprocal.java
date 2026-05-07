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
    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        // Array size = number of processes (one element per process)
        int[]    array      = new int[size];     // original array (root fills this)
        int[]    subArray   = new int[1];        // each process receives 1 element
        double[] localRecip = new double[1];     // each process computes 1 reciprocal
        double[] allRecip   = new double[size];  // root collects all reciprocals here

        // ── Root creates the array ────────────────────────────────────────────
        if (rank == 0) {
            System.out.println("\n[Root] Creating array with " + size + " elements:");
            System.out.print("  [ ");
            for (int i = 0; i < size; i++) {
                array[i] = i + 1;               // 1, 2, 3, 4 ... (avoid 0, can't divide)
                System.out.print(array[i] + " ");
            }
            System.out.println("]");
            System.out.println("[Root] Scattering one element to each process...\n");
        }

        // ── SCATTER: send one element to each process ─────────────────────────
        MPI.COMM_WORLD.Scatter(
                array,    0, 1, MPI.INT,
                subArray, 0, 1, MPI.INT,
                0
        );

        // ── Each process computes reciprocal of its element ───────────────────
        // reciprocal of x = 1/x
        localRecip[0] = 1.0 / subArray[0];

        System.out.printf("  [Process %d] Received element: %d  -->  Reciprocal = 1/%d = %.4f%n",
                rank, subArray[0], subArray[0], localRecip[0]);

        // ── GATHER: collect all reciprocals at root ───────────────────────────
        MPI.COMM_WORLD.Gather(
                localRecip, 0, 1, MPI.DOUBLE,
                allRecip,   0, 1, MPI.DOUBLE,
                0
        );

        // ── Root displays the resultant array ─────────────────────────────────
        if (rank == 0) {
            System.out.println("\n[Root] Original array:");
            System.out.print("  [ ");
            for (int i = 0; i < size; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.println("]");

            System.out.println("\n[Root] Resultant reciprocal array:");
            System.out.print("  [ ");
            for (int i = 0; i < size; i++) {
                System.out.printf("1/%d=%.4f  ", array[i], allRecip[i]);
            }
            System.out.println("]");
            System.out.println();
        }

        MPI.Finalize();
    }
}