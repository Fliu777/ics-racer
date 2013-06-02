/*
Frank Liu & Michael Zhang
Abstract Game Objects (player and environment)
Physics Racing Game
ICS4U
*/

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class GameObject implements Serializable {
	
	/*
	 * General Abstract Class with many components that would be common to nearly any object
	 * in the game. Variable names are more or less self explanatory
	 * */
	
	protected double xpos;
	protected double ypos;
	protected double velocity;
	protected double acceleration;
	protected double angle;
	protected final int IMAGESIZE;
	protected BufferedImage picture=null; 
	protected transient BufferedImage orig=null;
	
	protected boolean active;
	
	public GameObject(){
		IMAGESIZE=80;
		active=true;
	}
	
	public boolean collision(GameObject b){
		Rectangle obj1=new Rectangle((int)xpos, (int)ypos, IMAGESIZE, IMAGESIZE);
		Rectangle obj2=new Rectangle((int)b.xpos, (int)b.ypos, b.IMAGESIZE, b.IMAGESIZE);
		if (obj1.intersects(obj2))return true;
		return false;
	}
	
	public void draw(Graphics g){
		g.drawImage(picture,(int)xpos,(int)ypos,null);
		//g.drawRect( (int)xpos, (int)ypos, 50, 50);
	}
	
	public void kill(){
		active=false;
	}
	public boolean alive(){
		return active;
	}
	public double getX(){
		return xpos;
	}
	public double getY(){
		return ypos;
	}
	public double getAngle(){
		return angle;
	}
	public boolean dead(){
		return !active;
	}
	
}
