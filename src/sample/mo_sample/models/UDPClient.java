package sample.mo_sample.models;

import java.io.IOException;
import java.net.*;

public class UDPClient {
    private String playerNameInput ;
    private String color ;
    private DatagramSocket clientSocket ;
    private InetAddress IPAddress ;
    private byte[] sendData ;
    private byte[] recieveData ;
    private DatagramPacket sendPacket;
    private DatagramPacket receivePacket;

    public  UDPClient(String name,String color) throws UnknownHostException, SocketException {
        playerNameInput = name ;
        this.color = color ;
        clientSocket = new DatagramSocket();
        IPAddress = InetAddress.getByName("localhost");
        sendData = new byte[1024];
        recieveData = new byte[1024];
    }

    public void sendPacketToServer(String data) throws IOException {
        sendData = data.getBytes();
        sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1150);
        clientSocket.send(sendPacket);

    }

    public void recievePacketFromServer() throws IOException{
        receivePacket = new DatagramPacket(recieveData, recieveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println("FROM SERVER:" + modifiedSentence);
    }

    public void closeSocket(){
        clientSocket.close();
    }
}
