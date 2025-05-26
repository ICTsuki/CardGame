package main.java;

import main.java.game.nonsystem.NorthernPokerPlayer;

import java.io.*;
import java.net.Socket;

public class PokerClient {
    NorthernPokerPlayer player;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public PokerClient(Socket socket, String playerName) {
        try {
            this.socket = socket;
            player = new NorthernPokerPlayer(playerName);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
