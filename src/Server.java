import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

    List<String> connected_clients = new ArrayList<>(); //lista ip-adresa konektovanih klijenata

    public Server() throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(2022);

        while(true)
        {
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket,this);
            Thread thread = new Thread(serverThread);

            connected_clients.add(socket.getInetAddress().getHostAddress());

            thread.start();
        }

    }

    public static void main(String[] args) {
        try {
            new Server();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getConnected_clients() {
        return connected_clients;
    }

    public void setConnected_clients(List<String> connected_clients) {
        this.connected_clients = connected_clients;
    }
}
