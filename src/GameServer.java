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
	
	static ObjectOutputStream writer;
	static ObjectInputStream reader;
	
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
			while (active){
				System.out.println("i am server---------------------");
				client=server.accept();
				//hang until client is found.
				System.out.println("streams are being made");			
				writer= new ObjectOutputStream(client.getOutputStream());  
				reader = new ObjectInputStream(client.getInputStream());
				System.out.println("streams done");
				
				Thread comm=new Thread(new Communication());
			    comm.start();
			    
				//servertoclient();
			}
			
			
		}
		
		
		//port is already used, we assume the server did it, client starts

		else{
			System.out.println("i am client---------------------");
			
			try {
				ready=true;
				client=new Socket("localhost",12345);
				System.out.println("It be alive");
				
				reader = new ObjectInputStream(client.getInputStream());
				writer= new ObjectOutputStream(client.getOutputStream());  
				
				System.out.println("finished?");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("PLEASE--");
			} 
			
			//while (active){
		///		clienttoserver();
			//}
		}

	}	
	
	
	
	public static boolean ishoster(){
		return portuse;
	}
	
	public static boolean isready(){
		System.out.println("good to go");
		return ready;
	}
	
	public static void clienttoserver() throws IOException{
            try{
            	//System.out.println("---READDDD c to s");
    				writer.writeObject(GamePanel.Test);
    				//System.out.println("MY CAR IS "+GamePanel.Test);
    				//othercar = (PlayerCar) reader.readObject();
    				//System.out.println("im done");
    				System.out.println("here?");

    			
            }
            catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
				System.out.println("PasLEASE");
    		}
            finally{
    			try {
    				reader.close();
    				writer.close();
    				client.close();
    				server.close();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            }

	}
	public static PlayerCar getcar(){
		return othercar;
	}

	public static void servertoclient(){
		try {
				//System.out.println("---READDDD s to c");
				othercar = (PlayerCar) reader.readObject();
				writer.writeObject(GamePanel.Test);

			
		} catch (Exception e1) {
			System.out.println("IT FAILED PLEASE");
		}
		finally{
			active=false;
			try {
				writer.close();
				reader.close();
				client.close();
				server.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}



}

class Communication implements Runnable{

	@Override
	public void run() {
		System.out.println("hai");
		try{
			wait(1000);
			System.out.println("hi");
		}
		catch(Exception e){
			
		}
		
	}
	
}

