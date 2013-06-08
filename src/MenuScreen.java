/*
Frank Liu & Michael Zhang
Main Game Panel
Physics Racing Game
ICS4U
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MenuScreen extends JPanel implements MouseListener, MouseMotionListener {
	BufferedImage img = null;
	boolean mulbut, timebut,racebut,carbut;
	boolean mul, time,race,car;
	
	public MenuScreen() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		try {
		    img = ImageIO.read(new File("src/Images/menu.png"));
		} catch (IOException e) {
			System.err.println(e);
		}
		mulbut=timebut=racebut=carbut=false;
		mul= time=race=car=false;
	}
	public int getimgx(){
		return img.getWidth();
	}
	public int getimgy(){
		return img.getHeight();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g; 
		
		g2D.drawImage(img,0, 0,null );
		
		g2D.setColor(new Color(0,150,0));

		g2D.setStroke(new BasicStroke(8));
		//carbut=true;
		
		
		//detected mouse over
		int yadjust=-5;
		if (carbut==true) {
			g.drawLine(298,233+yadjust,470,233+yadjust);
		//	System.out.println("draw");
		}
		else if (racebut==true) {
			g.drawLine(470,233+yadjust,642,233+yadjust);
		}
		else if (timebut==true) {
			g.drawLine(642,233+yadjust,814,233+yadjust);
		}
		else if (mulbut==true) {
			g.drawLine(814,233+yadjust,986,233+yadjust);
		}
		repaint();
		
	}
	public void resetstate(){
		mul= time=race=car=false;
	}
	
	public int returnstate(){
		if (car){
			System.out.println("car state");
			resetstate();
			return 1;
		}
		if (time){
			resetstate();
			return 2;
		}
		if (race){
			resetstate();
			return 3;
		}
		if (mul){
			resetstate();
			return 4;
		}
		return -1;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println(arg0.getX()+" "+arg0.getY());
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		System.out.println(arg0.getX()+" "+arg0.getY());
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		int xpos=arg0.getX();
		int ypos=arg0.getY();
		if (ypos>=80 && ypos<=233){
			if (xpos<986 && xpos>298){
				//multiplayer button
				if (xpos>814) {
					mul=true;
				//	System.out.println("multiplayer");
				}
				
				//timetrial button
				else if (xpos>642) {
					time=true;
				//	System.out.println("time");
				}
				
				//race button
				else if (xpos>470) {
					race=true;
				//	System.out.println("race");
				}
				
				//career button
				else if (xpos>298){
					car=true;
				//	System.out.println("car");
				}
			}
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		int xpos=e.getX();
		int ypos=e.getY();
		mulbut=timebut=racebut=carbut=false;
		if (ypos>=80 && ypos<=233){
			if (xpos<986 && xpos>298){
				//multiplayer button
				if (xpos>814) {
					mulbut=true;
				//	System.out.println("multiplayer");
				}
				
				//timetrial button
				else if (xpos>642) {
					timebut=true;
				//	System.out.println("time");
				}
				
				//race button
				else if (xpos>470) {
					racebut=true;
				//	System.out.println("race");
				}
				
				//career button
				else if (xpos>298){
					carbut=true;
				//	System.out.println("car");
				}
			}
		}
		
	}


}
