import java.awt.Graphics;


public class PlayerCar extends GameObject {
	private String ign;
	
	public PlayerCar(){
		super();
		velocity=2;
		angle=0;
	}
	public void draw(Graphics g){
		super.draw(g);
		g.drawLine((int)xpos, (int)ypos, (int)(xpos+Math.cos(Math.toRadians(angle))*25), (int)(ypos+Math.sin(Math.toRadians(angle))*25));
	}
	public void moveforward(){
		xpos+=Math.cos(Math.toRadians(angle))*velocity;
		ypos+=Math.sin(Math.toRadians(angle))*velocity;
	}
	public void turnclock(){
		angle+=1;
	}
	public void turncounterclock(){
		angle-=1;
	}
	
	public void moveright(){
		xpos+=velocity;
	}
	public void moveleft(){
		xpos-=velocity;
	}
	public void moveup(){
		ypos-=velocity;
	}
	public void movedown(){
		ypos+=velocity;
	}
}




