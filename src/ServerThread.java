import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable{

    private Socket socket;
    private ServerMain server;

    public ServerThread(Socket socket, ServerMain server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {

        try {
            BufferedReader client_msg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter server_msg = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

            String available_clients = "";
            for(String str : server.getConnected_clients())
                available_clients += str+"\n";

            server_msg.println("Odaberite nekog od dostupnih klijenata");
            server_msg.println(available_clients);

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
