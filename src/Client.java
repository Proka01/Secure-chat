import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public Client() throws Exception
    {
        Scanner sc = new Scanner(System.in);

        InetAddress ip = InetAddress.getByName("localhost");

        Socket socket = new Socket(ip,2022);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    String msg = sc.nextLine();

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

        /*Socket socket = new Socket("localhost",2022);
        BufferedReader server_msg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter client_msg = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        Scanner sc = new Scanner(System.in);

        System.out.println(server_msg.readLine());
        System.out.println(server_msg.readLine());

        String msg;
        while (true){
            msg = sc.nextLine();
            client_msg.println(msg);
            if(msg.equals("choose")){
                msg = server_msg.readLine();
                System.out.println(msg);
            }
            else if(msg.equals("end"))break;
        }

        socket.close();*/
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
