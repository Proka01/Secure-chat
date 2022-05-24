import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private RSA rsa;

    public Client() throws Exception
    {
        rsa = new RSA();
        Scanner sc = new Scanner(System.in);

        InetAddress ip = InetAddress.getByName("192.168.0.29");

        Socket socket = new Socket(ip,2022);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // client1 -> client2 : start # client2
        // client2 primi da clint1 hoce da se javi i salje mu e,z
        // clinet1 racuna crypto_msg -> client2
        // client2 dekodira

        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    String msg = sc.nextLine();  // msg # client(i)
                    try {
                        dos.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    try {
                        String msg = dis.readUTF();
                        System.out.println(msg);



                    } catch (IOException e) {
                        e.printStackTrace();
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
