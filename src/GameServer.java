/*
Frank Liu & Michael Zhang
Multiplayer Server handling (main)
Physics Racing Game
ICS4U
*/

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameServer {

	/**
	 * @param args
	 */
	
	static boolean active=true;

	static ObjectInputStream  SERVERreader;
	static ObjectOutputStream  SERVERwriter;
	static ObjectInputStream  CLIENTreader;
	static ObjectOutputStream  CLIENTwriter;
	
	static Socket client;
	static ServerSocket server;
	static Socket serv;
	static boolean portuse=true;
	static PlayerCar othercar=null;
	static boolean ready=false;
	
	public static void main(String[] args) throws IOException {
		
		int port=12345;
		try{
			server=new ServerSocket(12345);

		}
		catch(Exception e){
			portuse=false;
		}
		System.out.println(":please?");
		MainLoop menu=new MainLoop();
		menu.setFocusable(true);

		//port has not been used, just make streams for server
		if (portuse){
			//while (active){
				System.out.println("i am server---------------------");
				client=server.accept();
				//hang until client is found.
				System.out.println("streams are being made");		
				
				
				SERVERwriter= new ObjectOutputStream(client.getOutputStream());  
				SERVERwriter.flush();
				
				SERVERreader = new ObjectInputStream(client.getInputStream());
				
				
				
				System.out.println("streams done");
				
				Thread comma=new Thread(new CommunicationServer());
			    comma.start();

			//}
			
			
		}
		
		
		//port is already used, we assume the server did it, client starts

		else{
			System.out.println("i am client---------------------");
			
			try {
				ready=true;
				client=new Socket("localhost",12345);
				
				System.out.println("It be alive");
				
				CLIENTwriter= new ObjectOutputStream(client.getOutputStream());  
				CLIENTwriter.flush();
				CLIENTreader = new ObjectInputStream(client.getInputStream());

				
				System.out.println("finished?");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("PLEASE--");
			} 
			
				Thread comm=new Thread(new CommunicationClient());
			    comm.start();
		}

	}	
	
	
	
	public static boolean ishoster(){
		return portuse;
	}
	
	public static boolean isready(){
		System.out.println("good to go");
		return ready;
	}
	
	public static void clienttoserver() {
            try{
            	//System.out.println("---READDDD c to s");
            	CLIENTwriter.writeObject(GamePanel.Test);
    				//System.out.println("MY CAR IS "+GamePanel.Test);
    			othercar = (PlayerCar) CLIENTreader.readObject();
    				//System.out.println("im done");
				System.out.println("here?");

    			
            }
            catch (Exception e) {
    			// TODO Auto-generated catch block
    			//e.printStackTrace();
            	active=false;
				System.out.println("closed by peer");
    			try {
    				CLIENTreader.close();
    				CLIENTwriter.close();
    				client.close();
    			} catch (IOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    		}

	}
	public static PlayerCar getcar(){
		return othercar;
	}

	public static void servertoclient(){
		try {
				//System.out.println("---READDDD s to c");
				othercar = (PlayerCar) SERVERreader.readObject();
			
				
				/*ONLY READ*/
				SERVERwriter.writeObject(GamePanel.Test);

			
		} 
		catch (Exception e1) {
			//e1.printStackTrace();
			System.out.println("closed by peer");
			active=false;
			try {
				SERVERwriter.close();
				SERVERreader.close();
				server.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		
	}

	
	static class CommunicationServer implements Runnable{
	
		@Override
		public void run() {
			
			System.out.println("hai=server");
			while (active){
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
	
	
	static class CommunicationClient implements Runnable{
	
		@Override
		public void run() {
			
			System.out.println("hai=client");
			while (active){
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


