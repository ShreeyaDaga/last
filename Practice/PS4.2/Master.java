import java.io.*;
import java.net.*;
import java.util.*;

public class Master{
    static int PORT = 8000;
    static int SYNC = 10;

    static List<Socket> slaves = Collections.synchronizedList(new ArrayList<>());
    static List<DataInputStream> inputs = Collections.synchronizedList(new ArrayList<>());
    static List<DataOutputStream> outputs = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Master is running");

        Thread acceptorThread = new Thread(() -> {
            while(true){
                try{
                    Socket slave = serverSocket.accept();
                    slaves.add(slave);

                    DataInputStream in = new DataInputStream(slave.getInputStream());
                    DataOutputStream out = new DataOutputStream(slave.getOutputStream());

                    inputs.add(in);
                    outputs.add(out);

                } catch(Exception e){
                    System.out.println(e);
                }
            }
        });
        acceptorThread.setDaemon(true);
        acceptorThread.start();

        Thread timeDaemon = new Thread(() -> {
            int round = 1;
            while (true) {
                try {
                    Thread.sleep(SYNC * 1000);

                    if(slaves.isEmpty()){
                        System.out.println("No Slaves connected yet");
                        return;
                    }

                    runBerkeleyAlgo(round);
                    round++;
        
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        timeDaemon.setDaemon(true);
        timeDaemon.start();

        Thread.currentThread().join();
    }

    static void runBerkeleyAlgo(int round) throws Exception{
        long masterTime = System.currentTimeMillis();
        System.out.println("Master's current time: " + masterTime + " ms");

        List<Long> timeDifference = new ArrayList<>();
        List<Integer> failedIndices = new ArrayList<>();

        for(int i = 0; i < slaves.size(); i++){
            try {
                outputs.get(i).writeUTF("TIME_REQUEST");
                outputs.get(i).flush();

                long slaveTime = inputs.get(i).readLong();
                long diff = slaveTime - masterTime;
                timeDifference.add(diff);
            } catch (Exception e) {
                failedIndices.add(i);
                System.out.println("Slave is no longer connected");
            }
        }

        for(int i = failedIndices.size() - 1; i >= 0; i--){
            int idx = failedIndices.get(i);
            slaves.remove(i);
            inputs.remove(i);
            outputs.remove(i);
            timeDifference.remove(i);
        }

        if (timeDifference.isEmpty()){
            System.out.println("No active slaves. Skipping this round");
            return;
        }
    }
}