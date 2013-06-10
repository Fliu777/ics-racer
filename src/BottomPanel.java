import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class BottomPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BottomPanel(){
		
	}
	public void paintComponent(Graphics g) {
		g.drawRect(0, 0, 400, 400);
		g.drawString("hi", 0, 100);
	}
}
