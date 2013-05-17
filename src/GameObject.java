import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public abstract class GameObject {
	protected double xpos;
	protected double ypos;
	protected int velocity;
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
	public void kill(){
		active=false;
	}
	public boolean dead(){
		return !active;
	}
}
