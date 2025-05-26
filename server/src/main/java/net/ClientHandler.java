package main.java.net;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private final Socket socket;
    int clientID;
    ObjectInputStream in;
    ObjectOutputStream out;

    public ClientHandler(Socket socket, int clientID) {
        this.socket = socket;
        this.clientID = clientID;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while(!socket.isClosed()) {
                Object input = in.readObject();
            }

        } catch (Exception e) {
           e.printStackTrace();
        }

    }

    public Socket getSocket() {
        return this.socket;
    }

}

