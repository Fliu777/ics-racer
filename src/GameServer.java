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
	static boolean portuse=true;
	static PlayerCar othercar;
	static boolean ready=false;
	
	public static void main(String[] args) throws IOException {
		
		int port=12345;
		try{
			server=new ServerSocket(port);

		}
		catch(Exception e){
			portuse=false;
		}
		System.out.println(":please?");
		MainLoop menu=new MainLoop();
		menu.setFocusable(true);
		
		//port has not been used, and therefore port must be created
		if (portuse){
			
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
				System.out.println("PLEASE");
			} 
			
			while (active){
				servertoclient();
			}
			
		}
		
		
		//port is already used, we assume the server did it

		else{
			client=server.accept();
			reader = new ObjectInputStream(client.getInputStream());
			writer= new ObjectOutputStream(client.getOutputStream());  
			while (active)clienttoserver();
			
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
    			while (true){
    				System.out.println("---READDDD c to s");
    				writer.writeObject(GamePanel.curcar());
    				othercar = (PlayerCar) reader.readObject();

    			}
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
			while (true){
				System.out.println("---READDDD s to c");
				othercar = (PlayerCar) reader.readObject();
				writer.writeObject(GamePanel.curcar());

			}
		} catch (Exception e1) {
			
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
