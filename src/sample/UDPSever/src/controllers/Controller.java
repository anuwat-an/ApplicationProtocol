package controllers;

public class Controller {

    public void startApp() {
        UDPServer udpServer = new UDPServer();
        try {
            udpServer.main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
