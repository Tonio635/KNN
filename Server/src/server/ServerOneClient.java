package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerOneClient extends Thread{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerOneClient(Socket s) throws IOException{
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        this.start();
    }
}
