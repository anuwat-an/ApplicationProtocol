package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;
    private ArrayList<Socket> clientSockets;

    public Server() throws IOException {
        serverSocket = new ServerSocket(5000);
        serverSocket.setSoTimeout(600000);
        waitForClient();
    }

    private void waitForClient() throws IOException {
        while (true) {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");

            /**
             * accept connection and create Socket
             */
            Socket server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());

            /**
             * get message from client (request)
             */
            DataInputStream in = new DataInputStream(server.getInputStream());
            String request = in.readUTF();
            System.out.println(request);

            /**
             * response to client
             */
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();
        }
    }

    public static void main(String[] args) throws IOException {
        /** user thread to create sockets & recieve msg from each socket */
        Server server = new Server();
    }

}
