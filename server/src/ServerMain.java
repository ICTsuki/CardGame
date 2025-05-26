import main.java.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        Server server = Server.getInstance();
        server.start(1234);
    }
}