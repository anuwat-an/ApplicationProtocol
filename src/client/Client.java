/**
 * Anuwat Angkuldee 5810401066
 */

package client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

import java.io.*;
import java.net.Socket;

public class Client {

    @FXML
    private GridPane loginPane;
    @FXML
    private GridPane userPane;
    @FXML
    private Label loginUser;
    @FXML
    private TextField user;
    @FXML
    private TextField pass;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextArea contents;
    @FXML
    private Label balanceLabel;
    @FXML
    private TextField browse;
    @FXML
    private Button browseButton;
    @FXML
    private Label productLabel;
    @FXML
    private Button buyButton;
    @FXML
    private TextField redeemCoin;
    @FXML
    private Button redeemCoinButton;

    private Socket socket;

    private String serverName = "localhost";
    private int port = 5000;

    private DataInputStream in;
    private DataOutputStream out;

    private String currentUser;

    public Client() throws IOException {
        socket = new Socket(serverName, port);

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void connectToServer() throws IOException {
//        System.out.println("Just connected to " + socket.getRemoteSocketAddress());
//
//        OutputStream outToServer = socket.getOutputStream();
//        DataOutputStream out = new DataOutputStream(outToServer);
//        out.writeUTF("Hello from " + socket.getLocalSocketAddress());
//
//        InputStream inFromServer = socket.getInputStream();
//        DataInputStream in = new DataInputStream(inFromServer);
//
//        System.out.println("Server says " + in.readUTF());
//        socket.close();
//    }

    @FXML
    public void register() throws IOException {
        String user = this.user.getText();
        String pass = this.pass.getText();

        if (!"".equals(user) && !"".equals(pass)) {
            if (!user.contains(" ") && !pass.contains(" ")) {
                out.writeUTF("090 REGISTER " + user + " " + pass);

                String[] response = in.readUTF().split(" ");
                if ("091".equals(response[0])) {
                    login();
                }
                else if ("092".equals(response[0])) {
                    createAlert("Register Error!", response[3].replace("_", " "));
                }
            }
            else {
                createAlert("Cannot Register", "User and Pass must not contain space(\" \")");
            }
        }
        else {
            createAlert("Invalid register data", "Please enter both user and pass.");
        }
    }

    @FXML
    public void login() throws IOException {
        String user = this.user.getText();
        String pass = this.pass.getText();

        if (!"".equals(user) && !"".equals(pass)) {
            out.writeUTF("000 LOGIN " + user + " " + pass);

            String[] response = in.readUTF().split(" ");
            if ("001".equals(response[0])) {
                this.currentUser = response[3];
                this.balanceLabel.setText(response[4]);
                this.user.setText("");
                this.pass.setText("");
//                loginButton.setDisable(true);

                this.loginPane.setVisible(false);
                this.userPane.setVisible(true);
                this.loginUser.setText(response[3]);
                loadProducts();
            }
            else if ("002".equals(response[0])) {
                createAlert("Login Error!", response[3].replace("_", " "));
            }
        }
        else {
            createAlert("Invalid login data", "Please enter both user and pass.");
        }
    }

    private void loadProducts() throws IOException {
        out.writeUTF("010 LOAD " + currentUser);

        String[] response = in.readUTF().split(" ");
        if ("011".equals(response[0])) {
            contents.clear();

            String res = in.readUTF();
            while (!".".equals(res)) {
                contents.appendText(res + "\n");

                res = in.readUTF();
            }
        }
        else if ("012".equals(response[0])) {
            createAlert("Load Error!", response[3].replace("_", " "));
        }
    }

    @FXML
    public void redeemCoin() throws IOException {
        String redeemCode = redeemCoin.getText();

        if (currentUser != null) {
            if (!"".equals(redeemCode)) {
                out.writeUTF("020 REDEEM " + currentUser + " " + redeemCode);

                String[] response = in.readUTF().split(" ");
                if ("021".equals(response[0])) {
                    balanceLabel.setText(response[3]);
                    redeemCoin.setText("");
                }
                else if ("022".equals(response[0])) {
                    createAlert("Redeem Error!", response[3].replace("_", " "));
                }
            }
            else {
                createAlert("Redeem Error!", "Please enter redeem code.");
            }
        }
        else {
            createAlert("Redeem Error!", "No user logging in.");
        }
    }

    @FXML
    public void browse() throws IOException {
        String browse = this.browse.getText().trim().replace(" ", "_");

        if (!"".equals(browse)) {
            out.writeUTF("030 BROWSE " + browse);

            String[] response = in.readUTF().split(" ");
            if ("031".equals(response[0])) {
                String productInfo = response[3].replace("_", " ") + " Coin.";
                productLabel.setText(productInfo);
            }
            else if ("032".equals(response[0])) {
                createAlert("Browse Error!", response[3].replace("_", " "));
//                productLabel.setText("-");
            }
        }
        else {
            createAlert("Nothing to Browse", "Please enter valid product name.");
        }
    }

    @FXML
    public void buy() throws IOException {
        String productInfo = productLabel.getText();

        if (currentUser != null) {
            if (!"-".equals(productInfo)) {
                String[] productInfos = productInfo.split(" ");
                String productName = productInfos[0];
                for (int i=1; i<productInfos.length-2; i++) {
                    productName += "_" + productInfos[i];
                }
                int productPrice = Integer.parseInt(productInfos[productInfos.length-2]);

                out.writeUTF("040 BUY " + currentUser + " " + productName + " " + productPrice);

                String[] response = in.readUTF().split(" ");
                if ("041".equals(response[0])) {
                    balanceLabel.setText(response[3]);
                    contents.appendText(productName.replace("_", " ") + "\n");
                }
                else if ("042".equals(response[0])) {
                    createAlert("Buy Error!", response[3].replace("_", " "));
                }
            }
            else {
                createAlert("Cannot buy", "Please browse/select a product first.");
            }
        }
        else {
            createAlert("Buy Error!", "No user logging in.");
        }
    }

    @FXML
    public void closeSocket() throws IOException {
        out.writeUTF("900 CLOSE SOCKET");

        String[] response = in.readUTF().split(" ");
        if ("901".equals(response[0])) {
            socket.close();
        }
    }

    public void createAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

}
