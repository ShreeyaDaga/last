import java.util.*;
import java.net.*;
import java.io.*;

public class Master{
    static int PORT = 8000;
    static List<Socket> slaves = new ArrayList<>();
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Master connected at PORT: " + PORT);

        System.out.print("How many slaves will connect?");
        int numSlaves = sc.nextInt();

        System.out.println("Waiting to accept slaves...");


        for(int i = 0; i < numSlaves; i++){
            Socket slave = serverSocket.accept();
            slaves.add(slave);
            System.out.println("Slave " + i + " connected");
        }

        runBerkeleyAlgo();

        for(Socket s: slaves) s.close();
        serverSocket.close();
        System.out.println("SYNC complete");
    }

    static void runBerkeleyAlgo() throws Exception{
        long masterTime = System.currentTimeMillis();
        System.out.println("Master current time: " + masterTime + " ms");

        List<DataInputStream> inputs = new ArrayList<>();
        List<DataOutputStream> outputs = new ArrayList<>();
        List<Long> timeDifference = new ArrayList<>();

        for(int i = 0; i < slaves.size(); i++){
            DataInputStream in = new DataInputStream(slaves.get(i).getInputStream());
            DataOutputStream out = new DataOutputStream(slaves.get(i).getOutputStream());

            inputs.add(in);
            outputs.add(out);

            out.writeUTF("TIME_REQUEST");
            out.flush();

            long slaveTime = in.readLong();
            System.out.println("Current Slave Time: " + slaveTime + " ms");

            long diff = slaveTime - masterTime;
            timeDifference.add(diff);
        }

        long sumDiff = 0;
        for(long d: timeDifference) sumDiff+=d;
        long avgDiff = sumDiff / (timeDifference.size() + 1);

        long newMasterTime = masterTime + avgDiff;

        for(int i = 0; i < slaves.size(); i++){
            long adjustment = avgDiff - timeDifference.get(i);
            outputs.get(i).writeLong(adjustment);
            outputs.get(i).flush();
        }
    }
}