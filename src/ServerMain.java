import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {

    List<String> connected_clients = new ArrayList<>(); //lista ip-adresa konektovanih klijenata

    List<ServerThread> conected = new ArrayList<>();

    public ServerMain() throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(2022);
        int i = 1;
        while(true)
        {
            Socket socket = serverSocket.accept();
            connected_clients.add(socket.getInetAddress().getHostAddress());

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            ServerThread serverThread = new ServerThread(socket,this, "client"+i,dis,dos);
            Thread thread = new Thread(serverThread);

            //System.out.println(socket.getInetAddress().getHostAddress());
            conected.add(serverThread);
            i++;

            thread.start();
        }

    }

    public static void main(String[] args) {
        try {
            new ServerMain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ServerThread> getConected() {
        return conected;
    }

    public List<String> getConnected_clients() {
        return connected_clients;
    }

    public void setConnected_clients(List<String> connected_clients) {
        this.connected_clients = connected_clients;
    }
}
