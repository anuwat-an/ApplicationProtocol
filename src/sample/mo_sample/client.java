package sample.mo_sample;//chakrit puitrakul 5610404291 sec1

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class client {
	private Socket socket;
	private JFrame frame;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;
	private JPanel panel_8;
	private JPanel panel_9;
	private JPanel Mpanel;
	private Integer location;
	private DataInputStream in;
	private DataOutputStream out;
	
	public client()throws IOException{
		
		location = 0;
		frame = new JFrame();
		frame.setTitle("TREASURE\r\n");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(500, 400);
		frame.setLayout(null);		
		mainpanel();
		btnpanel();
		button();	
		frame.setVisible(true);
		Connect();

	}

	public void Connect() throws UnknownHostException, IOException{
		 String serverAddress = JOptionPane.showInputDialog("Enter IP Address ");
		 Socket socket = new Socket(serverAddress,22000);
		 JOptionPane.showMessageDialog(null, " connected server");
		 in = new DataInputStream(socket.getInputStream());
		 out = new DataOutputStream(socket.getOutputStream());
	}
	public void sendMove(int lo) throws IOException{
		
		out.writeInt(lo);
	}
	public void mainpanel(){
		Mpanel = new JPanel();
		Mpanel.setBackground(new Color(124, 252, 0));
        Mpanel.setBounds(0, 0, 500, 400);
        Mpanel.setLayout(null);
        JLabel lblNewLabel = new JLabel("TREASURE HUNT!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Algerian", Font.BOLD, 30));
		lblNewLabel.setBounds(93, 11, 297, 38);
		Mpanel.add(lblNewLabel);
		
		JLabel lblSelectAreaFor = new JLabel("Select area for Search");
		lblSelectAreaFor.setForeground(new Color(65, 105, 225));
		lblSelectAreaFor.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectAreaFor.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 20));
		lblSelectAreaFor.setBounds(133, 60, 217, 33);
		Mpanel.add(lblSelectAreaFor);
        frame.add(Mpanel);
        
        
		}
	public void btnpanel(){
		panel_1 = new JPanel();
		panel_1.setBounds(20, 138, 130, 50);
		panel_1.setLayout(new CardLayout(0, 0));
		Mpanel.add(panel_1);
		
		panel_2 = new JPanel();
		panel_2.setBounds(180, 138, 130, 50);
		Mpanel.add(panel_2);
		panel_2.setLayout(new CardLayout(0, 0));
		
		panel_3 = new JPanel();
		panel_3.setBounds(335, 138, 130, 50);
		Mpanel.add(panel_3);
		panel_3.setLayout(new CardLayout(0, 0));
		
		panel_4 = new JPanel();
		panel_4.setBounds(20, 217, 130, 50);
		Mpanel.add(panel_4);
		panel_4.setLayout(new CardLayout(0, 0));
		
		panel_5 = new JPanel();
		panel_5.setBounds(180, 217, 130, 50);
		Mpanel.add(panel_5);
		panel_5.setLayout(new CardLayout(0, 0));
		
		panel_6 = new JPanel();
		panel_6.setBounds(335, 217, 130, 50);
		Mpanel.add(panel_6);
		panel_6.setLayout(new CardLayout(0, 0));
		
		panel_7 = new JPanel();
		panel_7.setBounds(335, 291, 130, 50);
		Mpanel.add(panel_7);
		panel_7.setLayout(new CardLayout(0, 0));
		
		panel_8 = new JPanel();
		panel_8.setBounds(180, 291, 130, 50);
		Mpanel.add(panel_8);
		panel_8.setLayout(new CardLayout(0, 0));
		
		panel_9 = new JPanel();
		panel_9.setBounds(20, 291, 130, 50);
		Mpanel.add(panel_9);
		panel_9.setLayout(new CardLayout(0, 0));
	}
	public void button(){
		
		JButton btnA = new JButton("A0");
		btnA.setFont(new Font("Rockwell", Font.PLAIN, 25));
		btnA.setBackground(new Color(218, 165, 32));
		
		panel_1.add(btnA, "name_261635481523421");
		btnA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				location = 1;
				try {
					sendMove(location);
					message(in.readInt());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			});    
	
		
		JButton btnA_1 = new JButton("A1");
		btnA_1.setFont(new Font("Rockwell", Font.PLAIN, 25));
		btnA_1.setBackground(new Color(218, 165, 32));
		panel_2.add(btnA_1, "name_263619324735176");
		btnA_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				location = 2;
				try {
					sendMove(location);
					message(in.readInt());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			});    
		
		JButton btnA_2 = new JButton("A2");
		btnA_2.setFont(new Font("Rockwell", Font.PLAIN, 25));
		btnA_2.setBackground(new Color(218, 165, 32));
		panel_3.add(btnA_2, "name_263633602659799");
		btnA_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				location = 3;
				try {
					sendMove(location);
					message(in.readInt());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			});  
		
		JButton btnB = new JButton("B0");
		btnB.setFont(new Font("Rockwell", Font.PLAIN, 25));
		btnB.setBackground(new Color(218, 165, 32));
		panel_4.add(btnB, "name_263636473723800");
		btnB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				location = 4;	
				try {
					sendMove(location);
					message(in.readInt());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			});  
		
		JButton btnB_1 = new JButton("B1");
		btnB_1.setFont(new Font("Rockwell", Font.PLAIN, 25));
		btnB_1.setBackground(new Color(218, 165, 32));
		panel_5.add(btnB_1, "name_263637886170180");
		btnB_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				location = 5;
				try {
					sendMove(location);
					message(in.readInt());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			});  
		
		
		JButton btnB_2 = new JButton("B2");
		btnB_2.setFont(new Font("Rockwell", Font.PLAIN, 25));
		btnB_2.setBackground(new Color(218, 165, 32));
		panel_6.add(btnB_2, "name_263643173505322");
		btnB_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				location = 6;
				try {
					sendMove(location);
					message(in.readInt());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			});  
		
		
		JButton btnC_2 = new JButton("C2");
		btnC_2.setFont(new Font("Rockwell", Font.PLAIN, 25));
		btnC_2.setBackground(new Color(218, 165, 32));
		panel_7.add(btnC_2, "name_263648892445285");
		btnC_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				location = 9;	
				try {
					sendMove(location);
					message(in.readInt());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			});  
		
		
		JButton btnC_1 = new JButton("C1");
		btnC_1.setFont(new Font("Rockwell", Font.PLAIN, 25));
		btnC_1.setBackground(new Color(218, 165, 32));
		panel_8.add(btnC_1, "name_263644802424429");
		btnC_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				location = 8;	
				try {
					sendMove(location);
					message(in.readInt());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			});  
		
		JButton btnC = new JButton("C0");
		btnC.setFont(new Font("Rockwell", Font.PLAIN, 25));
		btnC.setBackground(new Color(218, 165, 32));
		panel_9.add(btnC, "name_263647129632187");
		btnC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				location = 7;
				try {
					sendMove(location);
					//System.out.println("test");
					message(in.readInt());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			});  
	}
	public void message(int servermessage) throws IOException{
		int message = servermessage;
		//System.out.println(message);
		if (message == 500){
			
			JOptionPane.showMessageDialog(null,"You miss, Try again.");
		}
		else{
			JOptionPane.showMessageDialog(null,"You Found The Treasure!");
			int result = JOptionPane.showConfirmDialog(null,
			        "Are you sure you want to quit?",
			        "Confirm Quit", JOptionPane.YES_OPTION);
			if (result == JOptionPane.YES_OPTION) System.exit(0);
			socket.close();
			
		}
		
	}
	 public static void main(String[] args) throws IOException {
	     client f = new client();
	      
	      
	 }
}
