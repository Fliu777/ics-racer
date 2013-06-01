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
