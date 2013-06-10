/*

Frank Liu & Michael Zhang
Window/Frame handling
Physics Racing Game
ICS4U
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
		
		//ScreenWidth=500;
		//ScreenHeight=500;
		/*
		 * Initialize Frame here
		 */

		GameFrame = new JFrame();
		GameFrame.setSize(ScreenWidth, ScreenHeight);
		GameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GameFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

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
		
		FlowLayout lay = new FlowLayout();
		//GameFrame.setLayout(lay);


		GameFrame.add(menu);
		
		GameFrame.setVisible(true);
		int temp=menu.returnstate();
		while (temp==-1)temp=menu.returnstate();
		menu.setFocusable(false);
		GameFrame.remove(menu);
		GameFrame.setSize(ScreenWidth, ScreenHeight);
		GameFrame.setLocationRelativeTo(null);
		


		
		//menu choice one
		if (temp==2){

			System.out.println("here?");
	
			GamePanel game = new GamePanel();
			game.changesetting(2);
			game.setFocusable(true);
			GameFrame.add(game);
			GameFrame.setVisible(true);
			GameFrame.setResizable(true);
			game.requestFocusInWindow();
		}
		
		else if (temp==3){
			GamePanel game = new GamePanel();
			game.changesetting(3);
			game.setFocusable(true);
			GameFrame.add(game);
			GameFrame.setVisible(true);
			GameFrame.setResizable(true);
			game.requestFocusInWindow();
		}
		else if (temp==4){
			GameServer server=new GameServer(GameFrame);
			
		}
		//BottomPanel bot=new BottomPanel();
		//GameFrame.add(bot);





	}

	private static void changesetting(int i) {
		// TODO Auto-generated method stub
		
	}

}
