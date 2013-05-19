import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public abstract class GameObject {
	
	/*
	 * General Abstract Class with many components that would be common to nearly any object
	 * in the game. Variable names are more or less self explanatory
	 * */
	
	protected double xpos;
	protected double ypos;
	protected int velocity;
	protected int acceleration;
	protected double angle;
	protected final int IMAGESIZE;
	protected BufferedImage picture=null; 
	protected BufferedImage orig=null;
	
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
		g.drawRect( (int)xpos, (int)ypos, 50, 50);
	}
	
	public void kill(){
		active=false;
	}
	public boolean alive(){
		return active;
	}
	
}