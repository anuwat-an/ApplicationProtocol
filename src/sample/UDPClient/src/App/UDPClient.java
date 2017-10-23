package App;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient extends Thread{

    DatagramSocket clientSocket;
    InetAddress IPAddress;
    Controller controller;
    public UDPClient(Controller con)
    {
        this.controller  = con;
        try {
            clientSocket = new DatagramSocket();
            IPAddress = InetAddress.getByName("localhost");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void run()
    {
        while (true) {
            try {
                main();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void main() {
        try {
            byte[] receiveData = new byte[1024];
            System.out.println("in");
//            receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData()).trim();
            if (modifiedSentence.equals("push")) {
                controller.pushNum();
            }
            System.out.println("FROM SERVER:" + modifiedSentence);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void sent(String sentence)
    {
        byte[] sendData = new byte[1024];
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1997);
        try {
            clientSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
