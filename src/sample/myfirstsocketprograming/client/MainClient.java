package sample.myfirstsocketprograming.client;

import java.io.*;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class MainClient {
    public static void main( String[] args ) {
        String serverName = "localhost";
        int port = 13000;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            final Socket client = new Socket(serverName, port);
            boolean wait = true;
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            ReadThread read = null;

            while (true) {

                while (wait) {
                    try {
                        DataInputStream in = new DataInputStream(client.getInputStream());
                        while (true) {
                            String text = in.readUTF();
                            System.out.println(text);
                            if (text.startsWith("You are now talking with ")) {
                                wait = false;
                                read = new ReadThread(client, in);
                                read.start();
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                System.out.print("> ");
                BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                String text = bf.readLine();

                out.writeUTF(text);

                if (text.equals("BYE")) break;
            }
            read.interrupt();
            client.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
