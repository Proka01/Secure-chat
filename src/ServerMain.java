import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {

    List<String> connected_clients = new ArrayList<>(); //lista ip-adresa konektovanih klijenata

    public ServerMain() throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(2022);

        while(true)
        {
            Socket socket = serverSocket.accept();
            connected_clients.add(socket.getInetAddress().getHostAddress());

            ServerThread serverThread = new ServerThread(socket,this);
            Thread thread = new Thread(serverThread);

            System.out.println(socket.getInetAddress().getHostAddress());

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

    public List<String> getConnected_clients() {
        return connected_clients;
    }

    public void setConnected_clients(List<String> connected_clients) {
        this.connected_clients = connected_clients;
    }
}
