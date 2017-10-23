package sample.mo_sample;//chakrit puitrakul 5610404291 sec1

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class server {
	private Integer answer = (int) (Math.random() * 9 + 1);
	Socket socket;
	ServerSocket listener = new ServerSocket(22000);

	class runnaja implements Runnable {
		public void run() {
			DataInputStream din;
			DataOutputStream dout;
			while (true) {
				try {
					din = new DataInputStream(socket.getInputStream());
					dout = new DataOutputStream(socket.getOutputStream());
					int num = din.readInt();
					
					System.out.print(num);
					if (founditem(num, answer)) {
						
						dout.writeInt(100);

					} else {
						dout.writeInt(500);

					}
				} catch (IOException e) {
					
			            try {
							listener.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
			}
		}
	}

	public server() throws IOException {
		//ServerSocket listener = new ServerSocket(22000);
		socket = listener.accept();

		System.out.println("Treasure server is Ready");
		System.out.println(answer);
		JOptionPane.showMessageDialog(null, "client connect");

		
		runnaja thread = new runnaja();
		new Thread(thread).start();
	}

	public static void main(String[] args) throws IOException {
		new server();

	}

	public static boolean founditem(int btnclient, int ansserver) {
		if (btnclient == ansserver) {
			return true;
		}
		return false;

	}
}
