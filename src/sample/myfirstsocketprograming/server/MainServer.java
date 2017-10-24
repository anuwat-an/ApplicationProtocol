package sample.myfirstsocketprograming.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class MainServer extends Thread {
    private ServerSocket serverSocket;
    private ArrayList<ConnectionSocket> sockets;

    public MainServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
//        serverSocket.setSoTimeout(1000);
        sockets = new ArrayList<ConnectionSocket>();
    }

    public void run() {
        while(true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                SocketAddress clientAddr = server.getRemoteSocketAddress();
                System.out.println("Just connected to " + clientAddr);

                ConnectionSocket c = new ConnectionSocket(server);
                this.sockets.add(c);
                System.out.println("Peer List: " + sockets);
                c.start();
                if (sockets.size() % 2 == 0) {
                    this.sockets.get(this.sockets.size() - 2).setToClient2(this.sockets.get(this.sockets.size() - 1).getToClient1());
                    this.sockets.get(this.sockets.size() - 1).setToClient2(this.sockets.get(this.sockets.size() - 2).getToClient1());
                }
            }catch(SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }

    }

    public static void main(String [] args) {
        int port = 13000;
        try {
            Thread t = new MainServer(port);
            t.start();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
