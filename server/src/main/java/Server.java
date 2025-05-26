package main.java;



import javafx.fxml.FXMLLoader;
import main.java.controller.NorthernPokerController;
import main.java.controller.ThreeCardController;
import main.java.net.ClientHandler;
import java.io.*;
import java.net.*;
import java.util.*;


public class Server {
    private static Server instance;

    private ServerSocket serverSocket;
    private final List<ClientHandler> clients = new ArrayList<>();
    private boolean isRunning = true;

    private Server(){
        super();
    }

    public static Server getInstance(){
        if(instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public void start(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.out.println("Server started on: " + serverSocket.getLocalPort());

        FXMLLoader pokerLoader = new FXMLLoader(getClass().getResource("/main/resource/fxml/NorthernPoker.fxml"));
        FXMLLoader threeCardLoader = new FXMLLoader(getClass().getResource("/main/resource/fxml/ThreeCard.fxml"));

        NorthernPokerController pokerController = pokerLoader.getController();
        ThreeCardController threeCardController = threeCardLoader.getController();

        while(isRunning) {
            int clientID = getNextID();
            ClientHandler client = new ClientHandler(serverSocket.accept(), clientID);

            synchronized (clients) {
                clients.add(client);
            }
        }
    }

    synchronized int getNextID() {
        return clients.size() + 1;
    }

    public synchronized int getServerCount() {
        return clients.size();
    }



    public void stop() throws IOException {
        this.isRunning = false;

        if(this.serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }

        synchronized (clients) {
            for(ClientHandler client : clients) {
                client.getSocket().close();
            }
        }

        serverSocket.close();
    }

}
