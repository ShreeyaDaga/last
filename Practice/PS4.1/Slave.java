import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Slave{
    static int PORT = 8000;
    public static void main(String[] args) throws Exception{
        String masterIP = (args.length > 0) ? args[0] : "localhost";

        System.out.println("Connecting to Master at " + masterIP + " PORT: " + PORT);

        Socket socket = new Socket(masterIP, PORT);

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        String request = in.readUTF();
        System.out.println("request Received: " + request);

        long slaveTime = System.currentTimeMillis();
        System.out.println("Current Slave Time: " + slaveTime + " ms");
        out.writeLong(slaveTime);
        out.flush();

        long adjustment = in.readLong();

        long newSlaveTIme = slaveTime + adjustment;
        System.out.println("New Slave time: " + newSlaveTIme);

        socket.close();
        System.out.println("SYNC complete");

    }
}