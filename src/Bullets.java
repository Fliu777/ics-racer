import java.awt.Color;
import java.awt.Graphics;


public class Bullets extends GameObject{
	
	private boolean alive;
	private int damage;
	private int type;
	public Bullets(double startx, double starty, double angle){
		super();
		xpos=startx+50;
		ypos=starty+50;
		this.angle=angle;
		velocity=15;
		active=true;
	}
	
	public void move(){
		xpos+=Math.cos(Math.toRadians(angle))*velocity;
		ypos+=Math.sin(Math.toRadians(angle))*velocity;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.red);
		g.fillOval((int)xpos, (int)ypos, 5, 5);
	}
	public void check(){
		if (xpos<0 || ypos<0||xpos>MainLoop.ScreenWidth ||ypos>MainLoop.ScreenHeight)  
			active=false;
	}

	
}
