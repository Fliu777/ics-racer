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
	public static void main(String[] args) {



		Socket ipad = detectserver();

		//
		System.out.println(":please?");
		MainLoop menu = new MainLoop();
		menu.setFocusable(true);

		// port has not been used, just make streams for server
		if (ipad == null) {
			try {
				server = new ServerSocket(port);
			} catch (Exception e) {
				e.printStackTrace();
			}
			while (true) {


				System.out.println("i am server---------------------");
				try {
					client = server.accept();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("server closed please");
					System.exit(0);
				}
				// hang until client is found.
				
				try {
					SERVERwriter = new ObjectOutputStream(client.getOutputStream());
				} catch (IOException e) {e.printStackTrace();}
				try {
					SERVERwriter.flush();
				} catch (IOException e) {e.printStackTrace();}

				try {
					SERVERreader = new ObjectInputStream(
							client.getInputStream());
				} catch (IOException e) {e.printStackTrace();}

				//System.out.println("streams done");
				active = true;

				Thread comma = new Thread(new CommunicationServer());
				comma.start();
			}
		}

		// port is already used, we assume the server did it, client starts

		else {
			System.out.println("i am client---------------------");

			try {
				isserver = false;
				client = ipad;

				System.out.println("It be alive::" + ipad);

				CLIENTwriter = new ObjectOutputStream(client.getOutputStream());
				CLIENTwriter.flush();
				CLIENTreader = new ObjectInputStream(client.getInputStream());
				active = true;

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

	public static Socket detectserver() {
		for (int i = 1; i < 2; i++) {
			try {
				String myip = InetAddress.getLocalHost().getHostAddress();

				
				String temp = myip.substring(0, myip.lastIndexOf('.')+1) +Integer.toString(i);
				//String temp = "192.168.1." + Integer.toString(i);
				System.out.println("trying   " + temp + "   -=");
				// Socket c1=new Socket(temp,port);
				Socket c1 = new Socket();
				c1.connect(new InetSocketAddress(temp, port), 30);
				return c1;

			} catch (Exception e) {
			}
		}
		return null;
	}

	public static boolean isserver() {
		return isserver;
	}

	public static void clienttoserver() {
		PlayerCar othercar0;
		try {
			CLIENTwriter = new ObjectOutputStream(client.getOutputStream());
			CLIENTwriter.flush();
			CLIENTreader = new ObjectInputStream(client.getInputStream());
			
			//System.out.println("client to the server passing");
			CLIENTwriter.writeObject(GamePanel.Test);
			
			othercar0 = (PlayerCar) CLIENTreader.readObject();
			GamePanel.setcar(othercar0);

		} catch (Exception e) {
			clientcleanup();
		}

	}
	public synchronized static void setcar(PlayerCar othercar0){
		
		othercar =othercar0;
	}

	public static void clientcleanup() {
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

	public synchronized  static PlayerCar getcar() {
		return othercar;
	}

	public static void servertoclient() {
		try {
			SERVERwriter = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {e.printStackTrace();}
		try {
			SERVERwriter.flush();
		} catch (IOException e) {e.printStackTrace();}

		try {
			SERVERreader = new ObjectInputStream(
					client.getInputStream());
		} catch (IOException e) {e.printStackTrace();}

		
		//System.out.println("starting this part a");
		try {
			// System.out.println("---READDDD s to c");


			othercar = (PlayerCar) SERVERreader.readObject();
			
			//System.out.println("starting this part b");
			GamePanel.setcar(othercar);
			/* ONLY READ */
			//System.out.println("them--"+othercar);
			//othercar.angle+=5;
			//System.out.println("me--"+GamePanel.Test);
			SERVERwriter.writeObject(GamePanel.Test);

		} catch (Exception e1) {
			e1.printStackTrace();

			servercleanup();

		}

	}

	static class CommunicationServer implements Runnable {

		@Override
		public void run() {

			System.out.println("hai=server");
			while (active) {
				
				long starttime=System.currentTimeMillis();
				servertoclient();
				System.out.println(System.currentTimeMillis()-starttime);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Exited from active-=server");
		}

	}

	static class CommunicationClient implements Runnable {

		@Override
		public void run() {

			System.out.println("hai=client");
			while (active) {
				clienttoserver();

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Exited from active-=client");

		}

	}

}
