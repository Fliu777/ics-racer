import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.BitSet;

import javax.swing.JPanel;
import javax.swing.Timer;



public class GamePanel extends JPanel implements   ActionListener, KeyListener, MouseListener, MouseMotionListener{
	private Timer myTimer;
	public boolean dir=true;
	public boolean running;
	private BitSet keyBits = new BitSet(256);
	
	public GamePanel() {
		this.addMouseListener(this);

		this.addMouseMotionListener(this);
		this.addKeyListener(this);

	    Thread myrunnable = new Thread(new starthere());
	    myrunnable.start();
	    //(int type, int bullettype, int xpos, int ypos,int speed, int angle,boolean friendly){
	    
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
		// TODO Auto-generated method stub
		System.out.println(arg0);
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
		// TODO Auto-generated method stub
		
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
	public void keyPressed(KeyEvent arg0) {
		int key=arg0.getKeyCode();
		//System.out.println("why u no print");
		if (key == KeyEvent.VK_UP) {
			keyBits.set(KeyEvent.VK_UP);
		}
		if (key == KeyEvent.VK_DOWN) {
			keyBits.set(KeyEvent.VK_DOWN);

		}
		if (key == KeyEvent.VK_SPACE) {
			keyBits.set(KeyEvent.VK_SPACE);

		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		keyBits.flip(arg0.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
	public boolean isKeyPressed(final int keyCode) {
	     return keyBits.get(keyCode);
 }
	

	public void paintComponent(Graphics g) {

		repaint();

	//	System.out.println("hi");

	}
	
	class starthere implements Runnable{
		public void run() {
			while(true){
				try {
					Thread.sleep(150);
					System.out.println("started");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				running=true;
			}

		}
	}

	
	
}

