/*
Frank Liu & Michael Zhang
Main Game Panel
Physics Racing Game
ICS4U
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements   ActionListener, KeyListener, MouseListener, MouseMotionListener{
	public boolean dir=true;
	public boolean running;
	private BitSet keysPressed = new BitSet(256);
	private BitSet keysReleased = new BitSet(256);
	
	GameMap map = new GameMap();
	
	static PlayerCar Test=new PlayerCar();
	
	static PlayerCar Opponent=null;
	
	ArrayList<PlayerCar> AI;
	
	ArrayList<Bullets> BulletList;
	
	private int counter=0;
    private long start = 0;
    boolean opponentalive=false;
	
	/*Keybits here is a bitset that takes into account the various input keys that can be
	 * pressed. When they are pressed, the bit is set as true, and when let go it is set as 
	 * false. Allows for checking if multiple keys are held at same time
	 * */
	private int fps=170;
	
	public GamePanel() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);

		BulletList=new ArrayList<Bullets>();
		
		AI=new ArrayList<PlayerCar>();
		AICar temp=new AICar();
		AI.add((AICar)temp);
	   
	    Thread myrunnable = new Thread(new starthere());
	    myrunnable.start();
	    
	}

	@Override
	public void mouseDragged(MouseEvent arg0){
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Bullets temp=new Bullets(Test.getX(), Test.getY() , Math.toDegrees( Math.atan2(arg0.getY()-Test.getY(),arg0.getX() -Test.getX())));
		BulletList.add(temp);
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		//System.out.println("why u no print");
		keysPressed.set(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		keysPressed.clear(key);
		keysReleased.set(key);
	}
	
	public boolean isKeyPressed(int keyCode) {
	     return keysPressed.get(keyCode);
	}
	public boolean isKeyReleased(int keyCode) {
	     return keysReleased.get(keyCode);
	}
	
	public void keyinput(){
		for (int i=0;i<255;i++){
			if (isKeyPressed(i)){
				if (i==KeyEvent.VK_UP){
					Test.moveforward();
				}
				if (i==KeyEvent.VK_RIGHT){
					Test.turnclock();
				}
				if (i==KeyEvent.VK_LEFT){
					Test.turncounterclock();
				}
			}
		}
		
		for (int i=0;i<255;i++){
			if (isKeyReleased(i)){
				if (i==KeyEvent.VK_UP){
					System.out.println("released");
					Test.restart();
					keysReleased.clear(i);
				}
			}
		}
			
			
		
	}
	
	public void fpscap() {
	    double wait=1000.0/fps;
	    double used = System.currentTimeMillis() - start;
	    //System.out.println(used);
	    if (used < wait) {
	        try{
	        	Thread.sleep((long) (wait-used));
	        	System.out.println("waiting");
	        }
	        catch(Exception e){}
	    }
	    start = System.currentTimeMillis();
	}
	
	public synchronized static void setcar(PlayerCar othercar0){
		Opponent = othercar0;
	}
	
	public synchronized static void updatecar(Graphics g){
		if (Opponent!=null){
			//System.out.println("their cycle");
			Opponent.draw(g);
			//Opponent.move();
		}
	}

	
	public void paintComponent(Graphics g) {
		
			long temp=System.currentTimeMillis();
			keyinput();
			//g.setColor(Color.green);
			//g.fillRect(0, 0, MainLoop.ScreenWidth, MainLoop.ScreenHeight);

			//map.draw(g);

			g.setColor(Color.black);
			Test.draw(g);
			Test.move();
			
			//Opponent=GameServer.getcar();
			System.out.println("myclce");
			
		/*	if (Opponent!=null){
				//System.out.println("their cycle");
				Opponent.draw(g);
				//Opponent.move();
			}*/
			
			updatecar(g);

			
			
			for (int i=0;i<BulletList.size();i++){
				if (BulletList.get(i).dead()){
					BulletList.remove(i);
				}
			}
			for (int i=0;i<AI.size();i++){
				((AICar)(AI.get(i))).update();
				//((AICar)(AI.get(i))).draw(g);
			}
			
			
			for (int i=0;i<AI.size();i++){
				if (AI.get(i).dead()){
					AI.remove(i);
				}
			}
			
			for (int i=0;i<AI.size();i++){
				for (int j=0;j<BulletList.size();j++){
					if (AI.get(i).collision(BulletList.get(j)) ){
						AI.get(i).loselife();
						BulletList.get(j).kill();
					}
				}
			}
			
			for (int i=0;i<BulletList.size();i++){
				BulletList.get(i).check();
				BulletList.get(i).move();
				BulletList.get(i).draw(g);
			}
			running=false;
			//System.out.println(System.currentTimeMillis()-temp);

		
		//repaint();
		//fpscap();


	//	System.out.println("hi");

	}
	
	
	class starthere implements Runnable{
		public void run() {
		//	if (GameServer.isready()){
				System.out.println("GOOGGOOG");
				while(true){
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					running=true;
					repaint();
				}
		//	}

		}
	}
	
	

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

	
	
}

