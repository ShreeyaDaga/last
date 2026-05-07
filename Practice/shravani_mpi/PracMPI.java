import mpi.*;

public class PracMPI{
    public static void main (String[] args)
    {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size= MPI.COMM_WORLD.Size();

        int n=8;
        int chunksize=n/size;

        int[] send = null;

        int[] recv= new int[chunksize];

        if(rank==0)
        {
            send= new int[]{1,2,3,4,5,6,7,8};
        }
        else{
            send= new int[n];
        }

        MPI.COMM_WORLD.Scatter(send, 0, chunksize,MPI.INT, recv, 0, chunksize, MPI.INT, 0);

        double localsum=0;
        for(int i =0; i<recv.length;i++)
        {
            localsum+=recv[i];

        }

        double localavg= localsum/recv.length;
        double [] gather = new double[size];

        MPI.COMM_WORLD.Gather(new double[]{localavg}, 0, chunksize, MPI.DOUBLE,gather, 0, chunksize, MPI.DOUBLE,0);

        if(rank==0)
        {
            double finalsum=0;
            for(int i=0; i<gather.length;i++)
            {
                finalsum+=gather[i];
            }

            double finalavg= finalsum/size;

            System.out.println(finalavg);
        }

        double[] recp = new double[recv.length];

        for(int i=0;i<recv.length;i++)
        {
            recp[i]= 1/recv[i];
        }

        double[] result = new double[n];
        MPI.COMM_WORLD.Gather(recp, 0, 1, MPI.DOUBLE, result, 0,1, MPI.DOUBLE, 0);

        MPI.Finalize();


    }
}