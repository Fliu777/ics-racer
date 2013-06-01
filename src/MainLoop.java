/*
Frank Liu & Michael Zhang
Window/Frame handling
Physics Racing Game
ICS4U
*/

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainLoop extends JFrame{
	static JFrame GameFrame;
	static int ScreenWidth;
	static int ScreenHeight;
	public MainLoop(){
		ScreenWidth= java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
		ScreenHeight= java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
		/* Initialize Frame here
		 * 
		 */
		
		GameFrame=new JFrame();
		GameFrame.setSize(ScreenWidth,ScreenHeight);
		GameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			
		GameFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
		    	System.out.println("FINAL CLOSING OPERATIONS");
		       if (GameServer.isserver()==true){
		    	   //if (GameServer.active)
		    		   GameServer.servercleanup();
		       }
		       else{
		    	   //if (GameServer.active)
		    		   GameServer.clientcleanup();
		       }
		       GameFrame.dispose();
		       System.out.println("h");
		       System.exit(0);
		    }
		});

		
		GamePanel game=new GamePanel();
		game.setFocusable(true);
		GameFrame.add(game);
		
		/*MenuScreen game=new MenuScreen();
		game.setFocusable(true);
		GameFrame.ad
		
		
		
		
		
		
		
		
		d(game);*/
		
		
		GameFrame.setVisible(true);
		GameFrame.setResizable(true);
		
	}

}
