import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerThread implements Runnable{

    private Socket socket;
    private ServerMain server;
    private String name;
    private Boolean loggedin;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ServerThread(Socket socket, ServerMain server, String name, DataInputStream dis,DataOutputStream dos) {
        this.socket = socket;
        this.server = server;
        this.name = name;
        this.dis = dis;
        this.dos = dos;
        this.loggedin = true;
    }

    @Override
    public void run() {

        String msg;
        while (true) {
            try {
                msg = dis.readUTF();
                System.out.println(msg);

                if (msg.equals("logout")) {
                    this.loggedin = false;
                    this.socket.close();
                    break;
                }

                StringTokenizer st = new StringTokenizer(msg, "#");
                String msgToSend = st.nextToken();
                String rec = st.nextToken();

                for (ServerThread serverThread : server.getConected()) {
                    if (serverThread.getName().equals(rec) && serverThread.loggedin) {
                        serverThread.dos.writeUTF(this.name + ":" + msgToSend);
                        break;
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            this.dos.close();
            this.dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ServerMain getServer() {
        return server;
    }

    public void setServer(ServerMain server) {
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
