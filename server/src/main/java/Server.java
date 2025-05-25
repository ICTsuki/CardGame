package main.java;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Platform;



public class Server {

    ArrayList<ClientThread> clients = new ArrayList<>();
    TheServer server;
    boolean isRunning = true;
    ServerSocket serverSocket;

    synchronized int getNextID() {
        return clients.size() + 1;
    }

    public synchronized int getServerCount() {
        return clients.size();
    }

    Server() {
        this.server = new TheServer();
        this.server.start();
    }


    // closes all current connections and prevents server from accepting new connections
    public void stopServer() {
        this.isRunning = false; // Stop the server loop
        if (server != null && !server.isInterrupted()) {
            server.interrupt(); // Interrupt the server thread
        }

        // closes server socket to prevent new connections from being made
        try {
            if (this.serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
        catch (Exception e) {
            System.err.println("Error while closing server socket");
        }


        // closes all current client connections
        synchronized (clients) {
            for(ClientThread client : clients) {
                try {
                    client.connection.close();
                }
                catch (IOException e) {
                    System.err.println("Error closing clients" + e.getMessage());
                }
            }
            clients.clear();
            Platform.runLater(() -> {
                ServerInfo.getServerController().getEventList().getItems().add("CLOSED ALL CURRENT CLIENT CONNECTIONS");
            });
        }
    }


    // restarts server completely, allowing new connections if server was previously closed
    public void restartServer() {
        this.isRunning = true;

        this.server = new TheServer();
        this.server.start();
    }


    public class TheServer extends Thread {
        @Override
        public void run() {
            try (ServerSocket socket = new ServerSocket(ServerInfo.getPort());) {
                serverSocket = socket; // update member in 'Server' class

                // continuously accepts new connections and adds them to client list while server is running
                while (isRunning) {
                    try {
                        int clientID = getNextID();
                        ClientThread c = new ClientThread(socket.accept(), clientID);
                        synchronized (clients) {
                            clients.add(c);
                        }
                        Platform.runLater(() -> {
                            ServerInfo.getServerController().getClientCountLabel().setText(Integer.toString(getServerCount()));
                            ServerInfo.getServerController().getEventList().getItems().add("CLIENT HAS CONNECTED TO SERVER: CLIENT #" + clientID);
                        });
                        c.start();
                    } catch (IOException e) {
                        if (!isRunning) {
                            break;
                        }
                        else {
                            System.err.println("Error accepting new client connection");
                        }
                    }
                }
                System.out.println("Server has shut down.");
            } catch (IOException e) {
                System.err.println("Error with ServerSocket: " + e.getMessage());
            } finally {
                Platform.runLater(() -> {
                    ServerInfo.getServerController().getEventList().getItems().add("SERVER CLOSED");
                });
            }
        }//end of while
    }


    class ClientThread extends Thread {
        Socket connection;
        int clientID;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int clientID) {
            this.connection = s;
            this.clientID = clientID;
        }


        @Override
        public void run() {
            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.err.println("Client thread streams not opened");
            }
        }//end of run
    }//end of client thread
}