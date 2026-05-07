import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Slave{
    static int PORT = 8000;
    public static void main(String[] args) throws Exception{
        String masterIP = (args.length > 0) ? args[0] : "localhost";
        Socket socket = new Socket(masterIP, PORT);
        System.out.println("Slave connected to Master at " + masterIP + ": PORT = " + PORT);

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        int round = 1;
        while(true){
            try{
                String request = in.readUTF();
                System.out.println("Request recieved: " + request);

                long slaveTime = System.currentTimeMillis();
                out.writeLong(slaveTime);
                System.out.println("Sent current time to Master: " + slaveTime + " ms");

                long adjustment = in.readLong();
                long newSlaveTime = slaveTime + adjustment;

                if(adjustment > 0){
                    System.out.println("My clock was moving BEHIND. Adjusting clock to make it move FORWARD.");
                    System.out.println("Updated Slave Time: " + newSlaveTime + " ms");
                } else if(adjustment < 0){
                    System.out.println("My clock was moving AHEAD. Adjusting clock to make it move BACKWARD.");
                    System.out.println("Updated Slave Time: " + newSlaveTime + " ms");
                }
                else{
                    System.out.println("Clock is in SYNC");
                }

                round++;
            } catch(Exception e){
                System.out.println("Exception: " + e);
                break;
            }
        }

        socket.close();

    }
}