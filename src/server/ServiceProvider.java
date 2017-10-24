package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceProvider extends Thread {

    private Socket socket;
    private User currentUser; // necessary?
    private ArrayList<User> users = new ArrayList<>();
    private Map<String, String> products = new HashMap<>();
    private Map<String, String> redeemCodes = new HashMap<>();

    public ServiceProvider(Socket socket, ArrayList<User> users, Map<String, String> products, Map<String, String> redeemCodes) {
        this.socket = socket;
        this.users = users;
        this.products = products;
        this.redeemCodes = redeemCodes;
    }

    @Override
    public void run() {
        while (true) {
            try {

                DataInputStream in = new DataInputStream(socket.getInputStream());
                String[] request = in.readUTF().split(" ");

                if ("LOGIN".equals(request[0])) {
                    for (User user : users) {
                        if (user.getUser().equals(request[1]) && user.getPass().equals(request[2])) {
                            currentUser = user;

                            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                            out.writeUTF("LOGIN OK");
                            break;
                        }
                    }
                }
                else if ("LOAD".equals(request[0])) {
                    if (request[1].equals(currentUser.getUser())) {
                        for (String content : currentUser.getContents()) {
                            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                            out.writeUTF(content);
                        }
                    }
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF(".");
                }
                else if ("REDEEM".equals(request[0])) {
                    if (request[1].equals(currentUser.getUser()) && redeemCodes.containsKey(request[2])) {
                        /** add balance to user */
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF("REDEEM OK " + redeemCodes.get(request[2]));
                    }
                }
                else if ("BROWSE".equals(request[0])) {
                    if (products.containsKey(request[1])) {
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF("BROWSE OK");

                        String productInfo = request[1] + " " + products.get(request[1]) + " Coin";
                        out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF(productInfo);
                    }
                }
                else if ("BUY".equals(request[0])) {
                    if (request[1].equals(currentUser.getUser()) && products.containsKey(request[2]) && products.get(request[2]).equals(request[3])) {
                        /** subtract balance from user */
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF("BUY OK");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
