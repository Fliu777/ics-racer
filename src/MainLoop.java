/*
Frank Liu & Michael Zhang
Window/Frame handling
Physics Racing Game
ICS4U
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainLoop extends JFrame {
	static JFrame GameFrame;
	static int ScreenWidth;
	static int ScreenHeight;

	public static void main(String[] args) {

		ScreenWidth = java.awt.GraphicsEnvironment
				.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
		ScreenHeight = java.awt.GraphicsEnvironment
				.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
		/*
		 * Initialize Frame here
		 */

		GameFrame = new JFrame();
		GameFrame.setSize(ScreenWidth, ScreenHeight);
		GameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GameFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("FINAL CLOSING OPERATIONS");
				if (GameServer.isserver() == true) {
					// if (GameServer.active)
					GameServer.servercleanup();
					GameServer.removeserver();
				} else {
					// if (GameServer.active)
					GameServer.clientcleanup();
				}
				GameFrame.dispose();
				System.out.println("h");
				System.exit(0);
			}
		});
		
		//menu
		MenuScreen menu=new MenuScreen();
		menu.setFocusable(true);	
		GameFrame.setSize(menu.getimgx(),menu.getimgy());
		GameFrame.setLocationRelativeTo(null);

		GameFrame.add(menu);
		
		GameFrame.setVisible(true);
		int temp=menu.returnstate();
		while (temp==-1)temp=menu.returnstate();
		menu.setFocusable(false);
		GameFrame.remove(menu);
		GameFrame.setSize(ScreenWidth, ScreenHeight);

		
		//menu choice one
		if (temp==1){
			System.out.println("here?");
	
			GamePanel game = new GamePanel();
			game.setFocusable(true);
			GameFrame.add(game);
			GameFrame.setVisible(true);
			GameFrame.setResizable(true);
			game.requestFocusInWindow();
		}





	}

}
