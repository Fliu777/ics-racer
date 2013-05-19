import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements   ActionListener, KeyListener, MouseListener, MouseMotionListener{
	public boolean dir=true;
	public boolean running;
	private BitSet keyBits = new BitSet(256);
	
	/*Keybits here is a bitset that takes into account the various input keys that can be
	 * pressed. When they are pressed, the bit is set as true, and when let go it is set as 
	 * false. Allows for checking if multiple keys are held at same time
	 * */
	
	public GamePanel() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);

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
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
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
	public void keyReleased(KeyEvent e) {
		keyBits.flip(e.getKeyCode());
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
					//System.out.println("started");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				running=true;
			}

		}
	}

	
	
}

