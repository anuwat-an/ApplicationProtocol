/**
 * Anuwat Angkuldee 5810401066
 */

package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.Hashtable;

public class Server extends Thread {

    private int port = 5000;
    private ServerSocket serverSocket;

    static Vector<User> users = new Vector<>();
    static Hashtable<String, String> products = new Hashtable<>();
    static Hashtable<String, String> redeemCodes = new Hashtable<>();
//    private Vector<Thread> clients = new Vector<>();

    public Server() throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(600000);

        /** init users here */
        User user = new User("user", "password");
        User anuwat = new User("anuwat", "4102");
        User test = new User("test", "test");
        user.setBalance(8000000);
        anuwat.setBalance(1000000);
        test.setBalance(1000);

        users.add(user);
        users.add(anuwat);
        users.add(test);

        /** init products here */
        products.put("Deceit", "129");
        products.put("CSGO", "315");
        products.put("PUBG", "559");
        products.put("The Evil Within 2", "1940");

        /** init redeemCodes here */
        redeemCodes.put("xxxx-xxxx-xxxx-xxxx", "1000");
        redeemCodes.put("aaaa-aaaa-aaaa-aaaa", "100");
        redeemCodes.put("0000-0000-0000-0000", "100000");
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
        System.out.println("Server has started at port " + serverSocket.getLocalPort() + ".");
        while (true) {
            try {
                Socket server = serverSocket.accept();
                System.out.println("Connected to " + server.getRemoteSocketAddress());

                Thread clientService = new ServiceProvider(server);
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
