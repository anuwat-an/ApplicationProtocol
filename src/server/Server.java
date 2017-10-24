package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server extends Thread {

    private int port = 5000;
    private ServerSocket serverSocket;

    private ArrayList<User> users = new ArrayList<>();
    private Map<String, String> products = new HashMap<>();
    private Map<String, String> redeemCodes = new HashMap<>();
//    private ArrayList<Thread> clients = new ArrayList<>();

    public Server() throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(600000);

        /** init users here */
        /** init products here */
        /** init redeemCodes here */
    }

//    private void waitForClient() throws IOException {
//        while (true) {
//            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
//
//            /**
//             * accept connection and create Socket
//             */
//            Socket server = serverSocket.accept();
//            System.out.println("Just connected to " + server.getRemoteSocketAddress());
//
//            /**
//             * get message from client (request)
//             */
//            DataInputStream in = new DataInputStream(server.getInputStream());
//            String request = in.readUTF();
//            System.out.println(request);
//
//            /**
//             * response to client
//             */
//            DataOutputStream out = new DataOutputStream(server.getOutputStream());
//            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
//            server.close();
//        }
//    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket server = serverSocket.accept();

                Thread clientService = new ServiceProvider(server, users, products, redeemCodes);
//                clients.add(clientService);

                clientService.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Thread server = new Server();
        server.start();
    }

}
