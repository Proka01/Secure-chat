import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private boolean flag;

    public Client() throws Exception
    {
        Scanner sc = new Scanner(System.in);

        InetAddress ip = InetAddress.getByName("localhost");

        Socket socket = new Socket("localhost",2022);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // client1 -> client2 : start # client2
        // client2 primi da clint1 hoce da se javi i salje mu e,z
        // clinet1 racuna crypto_msg -> client2
        // client2 dekodira

        flag = false;

        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!flag)
                {
                    String msg = sc.nextLine();  // msg # client(i)
                    try {
                        if (msg.equals("logout")){
                            socket.close();
                            flag= true;
                            break;
                        }
                        dos.writeUTF(AES.encrypt(msg.split("#")[0])+"#"+msg.split("#")[1]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!flag)
                {
                    try {
                        String msg = dis.readUTF();
                        System.out.println(AES.decrypt(msg.split(":")[1])+ "#" + msg.split(":")[0]);

                    } catch (IOException e) {
                       // e.printStackTrace();
                    }
                }
            }
        });

        sendMessage.start();
        readMessage.start();

        //socket.close();
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
