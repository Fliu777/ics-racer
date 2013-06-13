/*
Frank Liu & Michael Zhang
Multiplayer Server handling (main)
Physics Racing Game
ICS4U
 */

import java.awt.Frame;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;

public class GameServer {
	static boolean active = false;
	
	static ObjectInputStream SERVERreader = null;
	static ObjectOutputStream SERVERwriter = null;
	static ObjectInputStream CLIENTreader = null;
	static ObjectOutputStream CLIENTwriter = null;

	static Socket client = null;
	static ServerSocket server = null;

	static PlayerCar othercar = null;
	static boolean isserver = true;
	static int port = 13254;

	public GameServer(JFrame GameFrame, int type, String name) {

		Socket ipad = detectserver();
		
		//creates game
		GamePanel game = new GamePanel(type,name);
		game.changesetting(2);
		game.setFocusable(true);
		GameFrame.add(game);
		GameFrame.setVisible(true);
		GameFrame.setResizable(true);
		game.requestFocusInWindow();

		// port has not been used, just make streams for server
		if (ipad == null) {
			try {
				server = new ServerSocket(port);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//waits for next client
			while (true) {

				//System.out.println("i am server---------------------");
				
				//code should hang here until the client becomes connected with the server
				try {
					client = server.accept();
				} catch (IOException e) {
					//server has closed, only possible exception
					System.out.println("Server has closed. Goodbye!");
					System.exit(0);
				}
				
				//client is found, only left to open reading and writing streams here;
				try {
					SERVERwriter = new ObjectOutputStream(client.getOutputStream());
					SERVERwriter.flush();
					SERVERreader = new ObjectInputStream(
							client.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}

				// streams have finished being open 
				//we can start now
				active = true;

				//create a thread to deal with the server communciation with client
				Thread comma = new Thread(new CommunicationServer());
				comma.start();
			}
		}

		// if here, port is already used, we assume the server did it, client starts

		else {
			//System.out.println("Client here");
			try {
				//it is not a server
				isserver = false;
				
				//client is the socket now
				client = ipad;
				
				
				//open streams for reading and writing
				CLIENTwriter = new ObjectOutputStream(client.getOutputStream());
				CLIENTwriter.flush();
				CLIENTreader = new ObjectInputStream(client.getInputStream());
				
				//can start
				active = true;
			} catch (Exception e) {
				// bad things happened
				e.printStackTrace();
			}
			
			//start thread for communication
			Thread comm = new Thread(new CommunicationClient());
			comm.start();
		}

	}

	public static Socket detectserver() {
		
		//looks through all ports to detect an existing server
		for (int i = 1; i < 255; i++) {
			try {
				
				//attempts to connect to it, with a time out of 30 ms if it fails
				
				String myip = InetAddress.getLocalHost().getHostAddress();

				String temp = myip.substring(0, myip.lastIndexOf('.') + 1)
						+ Integer.toString(i);
				// String temp = "192.168.1." + Integer.toString(i);
				System.out.println("trying   " + temp + "   -=");
				// Socket c1=new Socket(temp,port);
				Socket c1 = new Socket();
				c1.connect(new InetSocketAddress(temp, port), 30);
				return c1;

			} catch (Exception e) {
				//failed, keep on trying, i dont want anything printed here since itd be nasty
			}
		}
		//nothing must become server
		return null;
	}

	public static boolean isserver() {
		return isserver;
	}

	public static void clienttoserver() {
		PlayerCar othercar0;
		//communication
		try {
			CLIENTwriter = new ObjectOutputStream(client.getOutputStream());
			CLIENTwriter.flush();
			CLIENTreader = new ObjectInputStream(client.getInputStream());

			// System.out.println("client to the server passing");
			CLIENTwriter.writeObject(GamePanel.Test);

			othercar0 = (PlayerCar) CLIENTreader.readObject();
			GamePanel.setcar(othercar0);

		} catch (Exception e) {
			clientcleanup();
		}

	}

	public synchronized static void setcar(PlayerCar othercar0) {
		//synchronization stuff
		othercar = othercar0;
	}

	public static void clientcleanup() {
		
		//close client, have to delete a bunch of stuff
		active = false;
		System.out.println("closed from client");
		try {
			if (CLIENTreader != null)
				CLIENTreader.close();
			if (CLIENTwriter != null)
				CLIENTwriter.close();
			if (client != null)
				client.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void removeserver() {
		if (server != null)
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static void servercleanup() {
		System.out.println("closed from server");
		active = false;
		try {
			if (SERVERreader != null)
				SERVERreader.close();
			if (SERVERwriter != null)
				SERVERwriter.close();
			if (client != null)
				client.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static PlayerCar getcar() {
		return othercar;
	}

	public static void servertoclient() {
		try {
			SERVERwriter = new ObjectOutputStream(client.getOutputStream());
			SERVERwriter.flush();
			SERVERreader = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}


		// System.out.println("starting this part a");
		try {
			// System.out.println("---READDDD s to c");

			othercar = (PlayerCar) SERVERreader.readObject();

			// System.out.println("starting this part b");
			GamePanel.setcar(othercar);
			/* ONLY READ */
			// System.out.println("them--"+othercar);
			// othercar.angle+=5;
			// System.out.println("me--"+GamePanel.Test);
			SERVERwriter.writeObject(GamePanel.Test);

		} catch (Exception e1) {
			e1.printStackTrace();

			servercleanup();

		}

	}

	static class CommunicationServer implements Runnable {

		@Override
		public void run() {

			//System.out.println("hai=server");
			while (active) {

				long starttime = System.currentTimeMillis();
				servertoclient();
				System.out.println(System.currentTimeMillis() - starttime);

				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//System.out.println("Exited from active-=server");
		}

	}

	static class CommunicationClient implements Runnable {

		@Override
		public void run() {

			//System.out.println("hai=client");
			while (active) {
				clienttoserver();

				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//System.out.println("Exited from active-=client");

		}

	}

}
