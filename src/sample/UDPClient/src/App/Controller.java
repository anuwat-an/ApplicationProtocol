package App;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
    UDPClient udp = new UDPClient(this);
    private int num = 0;

    @FXML
    protected Button bntServer;


    @FXML
    protected Label labnum;


    @FXML
    public void initialize(){
        System.out.println("start");
        udp.start();
    }

    @FXML
    public void Push(ActionEvent event){
        sent("push");

    }

    @FXML
    public void ConServer(ActionEvent event){
        sent("hi");
        bntServer.setDisable(true);
    }
    //method Network

    public void sent(String sentence)
    {
        udp.sent(sentence);
    }

    public void pushNum()
    {
        num++;
//        labnum.setText(num+"");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labnum.setText(num+"");
            }
        });
        System.out.println(labnum.getText());
//        udp.start();
//        System.out.println("Complete");
    }
}
