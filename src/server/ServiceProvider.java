/**
 * Anuwat Angkuldee 5810401066
 */

package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServiceProvider extends Thread {

    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    private User currentUser;

    public ServiceProvider(Socket socket) {
        this.socket = socket;

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {

                String[] request = in.readUTF().split(" ");
                System.out.println(socket.getLocalSocketAddress() +
                        " Get request " + request[0] + " " + request[1] +
                        " from " + socket.getRemoteSocketAddress());

                if ("000".equals(request[0])) {
                    boolean found = false;
                    for (User user : Server.users) {
                        if (user.checkUser(request[2], request[3])) {
                            found = true;
                            if (!user.isLoggedin()) {
                                currentUser = user;

                                out.writeUTF("001 LOGIN OK " + currentUser.getUser() + " " + currentUser.getBalance());
                            }
                            else {
                                out.writeUTF("002 LOGIN REJECT this_user_is_already_logged_in.");
                            }
                            break;
                        }
                    }
                    if (!found) {
                        out.writeUTF("002 LOGIN REJECT wrong_user_or_pass.");
                    }
                }
                else if ("010".equals(request[0])) {
                    if (request[2].equals(currentUser.getUser())) {
                        out.writeUTF("011 LOAD OK SENDING_DATA_TIL_.");

                        for (String content : currentUser.getContents()) {
                            out.writeUTF(content);
                        }

                        out.writeUTF(".");
                    }
                    else {
                        out.writeUTF("012 LOAD REJECT wrong_user.");
                    }
                }
                else if ("020".equals(request[0])) {
                    if (request[2].equals(currentUser.getUser())) {
                        if (Server.redeemCodes.containsKey(request[3])) {
                            currentUser.addBalance(Integer.parseInt(Server.redeemCodes.get(request[3])));

                            out.writeUTF("021 REDEEM OK " + currentUser.getBalance());
                        }
                        else {
                            out.writeUTF("022 REDEEM REJECT invalid_code.");
                        }
                    }
                    else {
                        out.writeUTF("022 REDEEM REJECT wrong_user.");
                    }
                }
                else if ("030".equals(request[0])) {
                    String productName = request[2].replace("_", " ");

                    if (Server.products.containsKey(productName)) {
                        String response = "031 BROWSE OK " + request[2] + "_" + Server.products.get(productName);

                        out.writeUTF(response);
                    }
                    else {
                        out.writeUTF("032 BROWSE REJECT no_product_name_" + request[2] + "_available.");
                    }
                }
                else if ("040".equals(request[0])) {
                    String productName = request[3].replace("_", " ");

                    if (request[2].equals(currentUser.getUser())) {
                        if (Server.products.containsKey(productName)) {
                            if (Server.products.get(productName).equals(request[4])) {
                                if (!currentUser.getContents().contains(productName)) {
                                    if (currentUser.getBalance() >= Integer.parseInt(request[4])) {
                                        currentUser.subBalance(Integer.parseInt(request[4]));
                                        currentUser.addContent(productName);

                                        out.writeUTF("041 BUY OK " + currentUser.getBalance());
                                    }
                                    else {
                                        out.writeUTF("042 BUY REJECT not_enough_coin.");
                                    }
                                }
                                else {
                                    out.writeUTF("042 BUY REJECT you_already_own_this_product.");
                                }
                            }
                            else {
                                out.writeUTF("042 BUY REJECT product_prices_are_inconsistent");
                            }
                        }
                        else {
                            out.writeUTF("042 BUY REJECT product_was_removed.");
                        }
                    }
                    else {
                        out.writeUTF("042 BUY REJECT wrong_user.");
                    }
                }
                else if ("900".equals(request[0])) {
                    out.writeUTF("901 CLOSE OK SOCKET");
                    closeSocket();
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeSocket() {
        try {
            socket.close();
            System.out.println("Socket connecting to " + socket.getRemoteSocketAddress() + " is closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
