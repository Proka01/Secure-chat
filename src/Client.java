import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private RSA rsa;

    public Client() throws Exception
    {
        rsa = new RSA();
        Scanner sc = new Scanner(System.in);

        InetAddress ip = InetAddress.getByName("localhost");

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
                        if(msg.contains("start"))dos.writeUTF(msg);
                        else dos.writeUTF(rsa.encryptString(msg.split("#")[0]) + "#"+msg.split("#")[1]);
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

                        if(msg.contains("start")){
                            String reciever = msg.split(":")[0];
                            dos.writeUTF(rsa.getN()+";"+rsa.getE()+"#"+reciever);
                            System.out.println("slanje "+rsa.getN()+";"+rsa.getE()+"#"+reciever);
                        }
                        else if(msg.contains(";")){
                            String split[] = msg.split("[:;#]");
                            BigInteger reciever_n = new BigInteger(split[1]);
                            BigInteger reciever_e = new BigInteger(split[2]);
                            rsa.setN(reciever_n);
                            rsa.setE(reciever_e);
                        }
                        else{
                            System.out.println(rsa.decryptString(msg.split(":")[1]));
                        }

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
