package sample.myfirstsocketprograming.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadThread extends Thread {

    private Socket client;
    private DataInputStream in;
    public ReadThread(Socket client, DataInputStream in) {
        this.client = client;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String text = in.readUTF();
                System.out.println();
                System.out.println(text);
                System.out.print("> ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
