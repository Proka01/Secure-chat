import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public Client() throws Exception
    {
        Socket socket = new Socket("localhost",2022);
        BufferedReader server_msg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter client_msg = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        Scanner sc = new Scanner(System.in);

        System.out.println(server_msg.readLine());
        System.out.println(server_msg.readLine());

        socket.close();
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
