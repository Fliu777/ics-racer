/*
Frank Liu & Michael Zhang
Multiplayer Server handling (main)
Physics Racing Game
ICS4U
 */

import java.io.*;
import java.net.*;

public class GameServer {

	/**
	 * @param args
	 */

	static boolean active = true;

	static ObjectInputStream SERVERreader;
	static ObjectOutputStream SERVERwriter;
	static ObjectInputStream CLIENTreader;
	static ObjectOutputStream CLIENTwriter;

	static Socket client;
	static ServerSocket server;
	
	static Socket serv;
	
	static boolean portuse = true;
	static PlayerCar othercar = null;
	static boolean isserver = true;

	public static void main(String[] args) throws IOException {

		int port = 12345;

		String ipad = detectserver();

		portuse = false;
		System.out.println(":please?");
		MainLoop menu = new MainLoop();
		menu.setFocusable(true);

		// port has not been used, just make streams for server
		if (ipad.equals("")) {
			// while (active){

			try {
				server = new ServerSocket(12345);

			} catch (Exception e) {
				portuse = false;
			}

			System.out.println("i am server---------------------");
			client = server.accept();
			// hang until client is found.
			System.out.println("streams are being made");

			SERVERwriter = new ObjectOutputStream(client.getOutputStream());
			SERVERwriter.flush();

			SERVERreader = new ObjectInputStream(client.getInputStream());

			System.out.println("streams done");

			Thread comma = new Thread(new CommunicationServer());
			comma.start();

			// }

		}

		// port is already used, we assume the server did it, client starts

		else {
			System.out.println("i am client---------------------");

			try {
				isserver = false;
				client = new Socket(ipad, 12345);

				System.out.println("It be alive");

				CLIENTwriter = new ObjectOutputStream(client.getOutputStream());
				CLIENTwriter.flush();
				CLIENTreader = new ObjectInputStream(client.getInputStream());

				System.out.println("finished?");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("PLEASE--");
			}

			Thread comm = new Thread(new CommunicationClient());
			comm.start();
		}

	}

	public static String detectserver() {
		for (int i = 1; i < 255; i++) {
			try {
				String myip=InetAddress.getLocalHost().getHostAddress();
				
				//String temp = myip.substring(0, myip.lastIndexOf('.')+1) + Integer.toString(i);
				String temp="192.168.1."+Integer.toString(i);
				System.out.println("trying   " + temp+"   -=");
				// Socket c1=new Socket(temp,12345);
				Socket c1 = new Socket();
				long tstart=System.currentTimeMillis();

				c1.connect(new InetSocketAddress(temp, 12345), 20);
				System.out.println(System.currentTimeMillis()-tstart);
				c1.close();
				return temp;

			} catch (Exception e) {
			}
		}
		return "";
	}

	public static boolean ishoster() {
		return portuse;
	}

	public static boolean isserver(){
		return isserver;
	}

	public static void clienttoserver() {
		try {
			CLIENTwriter.writeObject(GamePanel.Test);
			othercar = (PlayerCar) CLIENTreader.readObject();


		} catch (Exception e) {
			clientcleanup();

		}

	}
	public static void clientcleanup(){
		active = false;
		System.out.println("closed by");
		try {
			CLIENTreader.close();
			CLIENTwriter.close();
			client.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public static void servercleanup() {
		System.out.println("closed by peer");
		active = false;
		try {
			SERVERwriter.close();
			SERVERreader.close();
			
			server.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PlayerCar getcar() {
		return othercar;
	}

	public static void servertoclient() {
		try {
			// System.out.println("---READDDD s to c");
			othercar = (PlayerCar) SERVERreader.readObject();

			/* ONLY READ */
			SERVERwriter.writeObject(GamePanel.Test);

		} catch (Exception e1) {
				servercleanup();
			
		}

	}

	static class CommunicationServer implements Runnable {

		@Override
		public void run() {

			System.out.println("hai=server");
			while (active) {
				servertoclient();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	static class CommunicationClient implements Runnable {

		@Override
		public void run() {

			System.out.println("hai=client");
			while (active) {
				clienttoserver();

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
