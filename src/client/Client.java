package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class Client {

    @FXML
    private TextField user;
    @FXML
    private TextField pass;
    @FXML
    private Button loginButton;
    @FXML
    private TableView<String> productTable;
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

    private String currentUser;

    public Client() throws IOException {
        socket = new Socket(serverName, port);
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
    public void login() throws IOException {
        String user = this.user.getText();
        String pass = this.pass.getText();

        if (!"".equals(user) && !"".equals(pass)) {
            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("000 LOGIN " + user + " " + pass);

            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            String[] response = in.readUTF().split(" ");
            if ("001".equals(response[0])) {
                this.currentUser = response[3];
                this.balanceLabel.setText(response[4]);
                this.user.setText("");
                this.pass.setText("");
//                loginButton.setDisable(true);
                loadProducts();
            }
        }
    }

    private void loadProducts() throws IOException {
        OutputStream outToServer = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
        out.writeUTF("010 LOAD " + currentUser);

        InputStream inFromServer = socket.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);

        String[] response = in.readUTF().split(" ");
        if ("011".equals(response[0])) {
            inFromServer = socket.getInputStream();
            in = new DataInputStream(inFromServer);

            String res = in.readUTF();
            while (!".".equals(res)) {
                productTable.getItems().add(res);

                inFromServer = socket.getInputStream();
                in = new DataInputStream(inFromServer);

                res = in.readUTF();
            }
        }
    }

    @FXML
    public void redeemCoin() throws IOException {
        if (currentUser != null) {
            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("020 REDEEM " + currentUser + " " + redeemCoin.getText());

            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            String[] response = in.readUTF().split(" ");
            if ("021".equals(response[0])) {
                balanceLabel.setText(response[3]);
                redeemCoin.setText("");
            }
        }
    }

    @FXML
    public void browse() throws IOException {
        String browse = this.browse.getText();

        if (!"".equals(browse)) {
            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("030 BROWSE " + browse);

            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            String[] response = in.readUTF().split(" ");
            if ("031".equals(response[0])) {
                String productInfo = response[3] + " " + response[4] + " Coin.";
                productLabel.setText(productInfo);
            }
        }
    }

    @FXML
    public void buy() throws IOException {
        String productInfo = productLabel.getText();

        if (currentUser != null && !"-".equals(productInfo)) {
            String[] productInfos = productInfo.split(" ");
            String productName = productInfos[0];
            int productPrice = Integer.parseInt(productInfos[1]);

            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("040 BUY " + currentUser + " " + productName + " " + productPrice);

            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            String[] response = in.readUTF().split(" ");
            if ("041".equals(response[0])) {
                balanceLabel.setText(response[3]);
                productTable.getItems().add(productName);
            }
        }
    }

}
