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
            out.writeUTF("LOGIN " + user + " " + pass);

            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            String response = in.readUTF();
            currentUser = user;
            if ("LOGIN OK".equals(response)) {
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
        out.writeUTF("LOAD " + currentUser);

        InputStream inFromServer = socket.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);

        String response = in.readUTF();
        while (!".".equals(response)) {
            productTable.getItems().add(response);

            inFromServer = socket.getInputStream();
            in = new DataInputStream(inFromServer);

            response = in.readUTF();
        }
    }

    @FXML
    public void redeemCoin() throws IOException {
        OutputStream outToServer = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
        out.writeUTF("REDEEM " + currentUser + redeemCoin.getText());

        InputStream inFromServer = socket.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);

        String response = in.readUTF();
        if ("REDEEM OK".equals(response.substring(0,9))) {
            balanceLabel.setText(Integer.parseInt(balanceLabel.getText())+Integer.parseInt(response.substring(9))+"");
            redeemCoin.setText("");
        }
    }

    @FXML
    public void browse() throws IOException {
        String browse = this.browse.getText();

        if (!"".equals(browse)) {
            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("BROWSE " + browse);

            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            String response = in.readUTF();
            if ("BROWSE OK".equals(response)) {
                inFromServer = socket.getInputStream();
                in = new DataInputStream(inFromServer);

                response = in.readUTF();
                productLabel.setText(response);
            }
        }
    }

    @FXML
    public void buy() {

    }

}
