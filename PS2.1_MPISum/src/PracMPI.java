import mpi.*;

/**
 *  👉 “Send = what I give”
    👉 “Receive = what I get”
    That’s it. Always think from the current process’s perspective.
 */

public class PracMPI {
    // throws Exception - handles MPI related runtime errors
    public static void main(String args[]) throws Exception {

        // Starts MPI environment
        MPI.Init(args); // Internally sets up communication

        int rank = MPI.COMM_WORLD.Rank(); // each process gets a unique ID
        int size = MPI.COMM_WORLD.Size(); // total number of processes

        int n = 8; // Total elements — MUST be divisible by size
        int chunkSize = n / size; // Each process gets this many elements

        int[] send = null; // full array (used by root)
        int[] recv = new int[chunkSize];  // Chunk recieved by each process

        // ROOT: initialize and display the array
        if (rank == 0) { // only root executes this block

            // initialises array
            send = new int[]{1, 2, 3, 4, 5, 6, 7, 8};

            // Displays entire array
            System.out.println("\n[Root] Array created with " + n + " elements:");
            for (int i = 0; i < n; i++)
                System.out.print("  array[" + i + "] = " + send[i]);

            System.out.println("\n\n[Root] Scattering " + chunkSize + " element(s) to each process...\n");  // indicates upcoming scatter operation
        } else {
            /**
             * MPJ requires non-null array on all processes
                Even though only root uses actual data
             */
            send = new int[n]; // Dummy buffer
        }

        // Distribute chunks of the array to each process
        /**
         * Internally MPJ coordinates automatically:
                Process 0 calls Scatter() → "I am root (rank 0), I will SEND chunks"
                Process 1 calls Scatter() → "I am a worker, I will RECEIVE my chunk"
                Process 2 calls Scatter() → "I am a worker, I will RECEIVE my chunk"
                Process 3 calls Scatter() → "I am a worker, I will RECEIVE my chunk"
         */
        // root use both send and recieve parameters, while worker igonores send parameters and uses recieve parameters
        MPI.COMM_WORLD.Scatter(send, 0, chunkSize, MPI.INT, recv, 0, chunkSize, MPI.INT, 0);
        /**
         * send      - send buffer - contains full data to be distributed - [1,2,3,4,5,6,7,8]
         * 0         - send offset - starting index in "send" array
         * chunkSize - send count  - number of elements to send to each process
         * MPI.INT   - sendtype    - Datatype of element in send, MPI compatible data type
         * recv      - recv buffer - Buffer where each process stores data recieved
         * 0         - recv offset - starting index in recieve buffer
         * chunkSize - recv count  - number of elements each process recieves
         * MPI.INT   - recv type   - Datatype of element in recv
         * 0         - root        - Rank of root process
         */

        // Each process computes its local (intermediate) sum
        int localSum = 0;
        for (int i = 0; i < recv.length; i++)
            localSum += recv[i];



        // Clean output
        for (int i = 0; i < size; i++) {
            MPI.COMM_WORLD.Barrier(); // wait for all processes
            if (rank == i) { // only 1 process prints at a time
                System.out.print("  [Process " + rank + "] Received: ");
                for (int j = 0; j < chunkSize; j++)
                    System.out.print(recv[j] + " ");
                System.out.println(" -->  Local Sum = " + localSum);
            }
        }

        // Reduce all local sums into a total sum at root
        int[] localSumArr = {localSum};  // convert localSum to array
        int[] totalSum = new int[1]; // stores final result at root


        // Combine all local sums into one total sum
        // In reduce, every process sends its data to the root
        // All processes SENDS(contributes) their values --> Root collects and combines them
        MPI.COMM_WORLD.Reduce(localSumArr, 0, totalSum, 0, 1, MPI.INT, MPI.SUM, 0);
        /**
         * localSumArr - send buffer - each process input data, process 0 - [3], process 1 - [7]...
         * 0           - send offset - start index in send buffer
         * totalSum    - recv buffer - Output array
         * 0           - recv offset - Start index in totalSum
         * 1           - count       - Number of elements to reduce | Here only 1 value per process
         * MPI.INT     - datatype    - Datatype of element
         * MPI.SUM     - operation   - Operation to perform
         * 0           - root        - Rank of root process
         */



        // Root displays the final total
        if (rank == 0) {
            System.out.println("\n[Root] Total Sum of all elements = " + totalSum[0]);
        }

        // Ends MPI execution and releases resources
        MPI.Finalize();
    }
}

