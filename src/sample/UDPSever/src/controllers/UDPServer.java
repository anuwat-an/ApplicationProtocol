package controllers;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class UDPServer {
    public void main() throws Exception
    {
        DatagramSocket serverSocket = new DatagramSocket(1997);
        ArrayList<InetAddress> listIPA = new ArrayList<>();
        ArrayList<Integer> listPort = new ArrayList<>();
        InetAddress IPAddress= null;
        int port= 0;
        boolean clientOne = false;
        boolean clientTwo = false;
        while(true)
        {
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String( receivePacket.getData()).trim();
            System.out.println("RECEIVED: " + sentence);
            IPAddress = receivePacket.getAddress();
            port = receivePacket.getPort();
            sendData = sentence.getBytes();



            if (clientOne==false && sentence.equals("hi"))
            {
                System.out.println(IPAddress+"   "+port);
                System.out.println("C1 Input");
                clientOne=true;
                listIPA.add(IPAddress);
                listPort.add(port);
                continue;
            }
            else if (clientTwo==false && sentence.equals("hi"))
            {
                System.out.println("C2 Input");
                clientTwo=true;
                listIPA.add(IPAddress);
                listPort.add(port);
                continue;
            }
            else if (clientOne==true && clientTwo!=true)
            {
                System.out.println(listIPA.get(0) + "  "+listPort.get(0));
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, listIPA.get(0), listPort.get(0));
                serverSocket.send(sendPacket);
                System.out.println("sent ClientOne Only");
            }
            else if (clientOne==true && clientTwo==true)
            {
                System.out.println("sent Client One and Two");
                for (int i=0;i<listIPA.size();i++) {
                    DatagramPacket sendPacket =
                            new DatagramPacket(sendData, sendData.length, listIPA.get(i), listPort.get(i));
                    serverSocket.send(sendPacket);
                }
            }
        }
    }
}
