//package sample.mo_sample.controllers;
//
//
//import com.sun.deploy.util.SessionState;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import main.java.models.UDPClient;
//
//import java.io.IOException;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//
//
//public class MainPageController {
//
//    @FXML
//    private TextField nameField ;
//    @FXML
//    private ComboBox<String> colorCombobox;
//
//    private UDPClient client ;
//
//    public MainPageController() {
//    }
//
//    @FXML
//    private void initialize(){
//        colorCombobox.getItems().addAll("Red", "Green", "Blue", "Yellow", "Black", "White");
//        colorCombobox.getSelectionModel().selectFirst();
//    }
//
//    @FXML
//    private void handleBtnConnect() throws IOException {
//        createClient(nameField.getText(),colorCombobox.getValue());
//        nameField.clear();
////        colorCombobox.getSelectionModel().select(2);
//    }
//
//    private void createClient(String name,String color) throws IOException {
//        client = new UDPClient(name,color) ;
//        client.sendPacketToServer(name);
//        client.recievePacketFromServer();
//        client.sendPacketToServer(color);
//        client.recievePacketFromServer();
//        createPage(name,color);
//        System.out.println("test");
//
//
//    }
//
//    private void createPage(String name,String color){
//        Stage stage = new Stage();
////        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientGamePage.fxml"));
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("/clientGamePage.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 322, 391);
//
//            stage.setTitle("New Window");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
