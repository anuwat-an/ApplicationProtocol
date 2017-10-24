package sample.myfirstsocketprograming.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

public class ConnectionSocket extends Thread {
    private Socket toClient1;
    private SocketAddress clientAddr;
    private Socket toClient2;
    private DataInputStream in;
    private DataOutputStream out;
    public ConnectionSocket(Socket server) {
        this.toClient1 = server;
        this.clientAddr = server.getRemoteSocketAddress();
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(toClient1.getInputStream());
            out = new DataOutputStream(toClient1.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (toClient2 == null) {
                out.writeUTF("waiting for other people...");
                System.out.println("Send to " + toClient1.getRemoteSocketAddress() + " : " + "waiting for other people...");
                while (toClient2 == null) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            out.writeUTF("You are now talking with " + toClient2.getRemoteSocketAddress());
            System.out.println("Send to " + toClient1.getRemoteSocketAddress() + " : " + "You are now talking with " + toClient2.getRemoteSocketAddress());
            out = new DataOutputStream(toClient2.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                String textFromClient = in.readUTF();
                System.out.println("From " + toClient1.getRemoteSocketAddress() + ": " + textFromClient);
                String answerToClient = toClient1.getRemoteSocketAddress() + "> " + textFromClient;
                System.out.println("Send to " + toClient2.getRemoteSocketAddress() + ": " + answerToClient);
                out.writeUTF(answerToClient);
                if (textFromClient.equals("BYE")) break;
            }catch(IOException e) {
                System.err.println("Server is down");
                break;
            }
        }
        try {
            toClient1.close();
            System.out.println("Close connection with " + clientAddr);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public void setToClient2(Socket toClient2) {
        this.toClient2 = toClient2;
    }

    @Override
    public String toString() {
        return toClient1.toString();
    }

    public Socket getToClient1() {
        return toClient1;
    }
}
