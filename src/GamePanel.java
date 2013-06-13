/*
Frank Liu & Michael Zhang
Main Game Panel
Physics Racing Game
ICS4U
 */

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener, KeyListener,
		MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3204711335077901765L;
	public boolean dir = true;
	public boolean running;


	public static BufferedImage[] cars = new BufferedImage[1];

	static GameMap map = new GameMap();

	static PlayerCar Test = new PlayerCar();

	static PlayerCar Opponent = null;

	ArrayList<PlayerCar> AI;

	ArrayList<Bullets> BulletList;
	
	private int setting=-1;
	
	
	long starttime;
	
	private BitSet keysPressed = new BitSet(256);
	private BitSet keysReleased = new BitSet(256);

	/*
	 * keysPressed/keysReleased here is a bitset that takes into account the various input keys
	 * that can be pressed. When they are pressed, the bit is set as true, and
	 * when let go it is set as false. Allows for checking if multiple keys are
	 * held at same time
	 */

	public GamePanel(int cartype, String playername) {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);

		BulletList = new ArrayList<Bullets>();

		AI = new ArrayList<PlayerCar>();
		AICar temp = new AICar();
		AI.add((AICar) temp);

		// initializes different types of cars, useful for when there are
		// multiple over server, as saves
		// the need to transmit images

		BufferedImage orig = null;
		try {
			orig = ImageIO.read(new File("src/Images/car"+cartype+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		cars[0] = orig;
		
		map.mapConvert(map.findX(0),map.findX(1));

		Thread myrunnable = new Thread(new starthere());
		myrunnable.start();

	}
	public void changesetting(int set){
		System.out.println("changed");
		setting=set;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

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
		Bullets temp = new Bullets(Test.getX(), Test.getY(),
				Math.toDegrees(Math.atan2(arg0.getY() - Test.getY(),
						arg0.getX() - Test.getX())));
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
		int key = e.getKeyCode();
		// System.out.println("why u no print");
		keysPressed.set(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		keysPressed.clear(key);
		keysReleased.set(key);
	}

	public boolean isKeyPressed(int keyCode) {
		return keysPressed.get(keyCode);
	}

	public boolean isKeyReleased(int keyCode) {
		return keysReleased.get(keyCode);
	}

	public void keyinput() {
		for (int i = 0; i < 255; i++) {
			if (isKeyPressed(i)) {
				if (i == KeyEvent.VK_UP) {
					Test.moveforward();
				}
				else if (i == KeyEvent.VK_DOWN) {
					Test.movebackward();
				}
				else  if (i == KeyEvent.VK_RIGHT) {
					Test.turnclock();
				}
				else  if (i == KeyEvent.VK_LEFT) {
					Test.turncounterclock();
				}
				else if (i == KeyEvent.VK_SPACE) {
					Test.restart();
				}
			}
		}

		for (int i = 0; i < 255; i++) {
			if (isKeyReleased(i)) {
				if (i == KeyEvent.VK_UP) {
					//System.out.println("released");
					Test.restart();
					keysReleased.clear(i);
				}
				else if (i == KeyEvent.VK_DOWN) {
					//System.out.println("released back");
					Test.restart();
					keysReleased.clear(i);
				}
			}
		}

	}

	public synchronized static void setcar(PlayerCar othercar0) {
		Opponent = othercar0;
	}

	public synchronized static void updatecar(Graphics g) {
		
		//checks if opponent exists, if not, then nothing happens
		//if it does, it draw the opponent relative to player
		if (Opponent != null) {
			// System.out.println("their cycle");
			Opponent.move(map);
			Opponent.drawrel(g,Test);
			// Opponent.move();
		}
	}

	public void paintComponent(Graphics g) {
		
		//the thread uses running to control the speed, so this is only painted every 50 ms
		
		if (running){
			keyinput();
			map.draw(g);	
	
			g.setColor(Color.black);
	
			//drawing and moving your car
			Test.draw(g);
			Test.move(map);
			
			
	//opponents car
			updatecar(g);
			g.setColor(Color.black);
			Font F = new Font("plain",Font.BOLD,30);
			this.setFont(F);
	
			
			
			g.drawString("Speed is: "+ Integer.toString(   (int) (Test.getvelocity()*10) )+"km/h", MainLoop.ScreenWidth-300, 50);
			//System.out.println(setting);
			
			//display time
			if (setting==3){
				//System.out.println("tinme");
				int timegiven=map.gettime()*100;
				long passed=System.currentTimeMillis()-starttime;
				int passedsec=(int) (passed/10);
				if ((timegiven-passedsec)/100.0>0)g.drawString(Double.toString((timegiven-passedsec)/100.0), 50, 50);
				else{
					g.drawString("Out of time", 50, 50);
				}
				
			}
			
			for (int i = 0; i < BulletList.size(); i++) {
				if (BulletList.get(i).dead()) {
					BulletList.remove(i);
				}
			}
			
			//AI code 
			/*for (int i = 0; i < AI.size(); i++) {
				((AICar) (AI.get(i))).update();
				((AICar) (AI.get(i))).draw(g);
			}
	
			for (int i = 0; i < AI.size(); i++) {
				if (AI.get(i).dead()) {
					AI.remove(i);
				}
			}
	
			for (int i = 0; i < AI.size(); i++) {
				for (int j = 0; j < BulletList.size(); j++) {
					if (AI.get(i).collision(BulletList.get(j))) {
						AI.get(i).loselife();
						BulletList.get(j).kill();
					}
				}
			}*/
	
			for (int i = 0; i < BulletList.size(); i++) {
				BulletList.get(i).check();
				BulletList.get(i).move();
				BulletList.get(i).draw(g);
			}
			//lets thread take control again
			running = false;
		}


		

	}

	class starthere implements Runnable {
		public void run() {
			starttime=System.currentTimeMillis();
			// if (GameServer.isready()){
			System.out.println("GOOGGOOG");
			while (true) {
				//System.out.println("hi");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//controls speed of paint
				running = true;
				repaint();
			}
			// }

		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
