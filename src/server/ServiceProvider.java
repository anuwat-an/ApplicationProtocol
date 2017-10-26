package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServiceProvider extends Thread {

    private Socket socket;
    private User currentUser;

    public ServiceProvider(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {

                DataInputStream in = new DataInputStream(socket.getInputStream());
                String[] request = in.readUTF().split(" ");

                if ("000".equals(request[0])) {
                    boolean found = false;
                    for (User user : Server.users) {
                        if (user.checkUser(request[2], request[3])) {
                            currentUser = user;
                            found = true;

                            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                            out.writeUTF("001 LOGIN OK " + currentUser.getUser() + " " + currentUser.getBalance());
                            break;
                        }
                    }
                }
                else if ("010".equals(request[0])) {
                    if (request[2].equals(currentUser.getUser())) {
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF("011 LOAD OK SENDING_DATA");

                        for (String content : currentUser.getContents()) {
                            out = new DataOutputStream(socket.getOutputStream());
                            out.writeUTF(content);
                        }

                        out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF(".");
                    }
                }
                else if ("020".equals(request[0])) {
                    if (request[2].equals(currentUser.getUser()) && Server.redeemCodes.containsKey(request[3])) {
                        currentUser.addBalance(Integer.parseInt(Server.redeemCodes.get(request[3])));

                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF("021 REDEEM OK " + currentUser.getBalance());
                    }
                }
                else if ("030".equals(request[0])) {
                    if (Server.products.containsKey(request[2])) {
                        String response = "031 BROWSE OK " + request[2] + " " + Server.products.get(request[2]);

                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF(response);
                    }
                }
                else if ("040".equals(request[0])) {
                    if (request[2].equals(currentUser.getUser()) &&
                            Server.products.containsKey(request[3]) &&
                            Server.products.get(request[3]).equals(request[4])) {

                        currentUser.subBalance(Integer.parseInt(request[4]));
                        currentUser.addContent(request[3]);

                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF("041 BUY OK " + currentUser.getBalance());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
