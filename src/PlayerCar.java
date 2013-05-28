import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PlayerCar extends GameObject {
	private String ign;
	protected double vx;
	protected double vy;
	protected double fric;
	protected double turnSpeed;
	protected double power;
	
	protected double pcap;
	protected double curvx, curvy;
	protected double accelrate;
	protected int life;
	public PlayerCar(){
		super();
		
		life=100;
		
		
		velocity=0;
		angle=0;
		pcap=2.5;
		accelrate=0.001;
		
		turnSpeed=0.45;
		fric=0.989;
		//fric=1;
		power=0.75;
		vx= cos(angle)*velocity;
		vy= sin(angle)*velocity;
		try {
			orig= ImageIO.read(new File("src/Images/car1.png"));
			picture=orig;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void draw(Graphics g){

		
		super.draw(g);
		g.setColor(Color.black);
		//g.drawString( Double.toString(Math.sqrt(vx*vx+vy*vy)) , 200, 200);
		double temp=Math.sqrt(vx*vx+vy*vy);
		//g.drawImage(picture, (int)xpos, (int)ypos, null);
		//g.drawLine((int)xpos, (int)ypos, (int)(xpos+cos(angle)*25), (int)(ypos+sin(angle)*25));
	}
	public void move(){
		rotate();
		vx *= fric;
		vy *= fric;
	
		//Velocity cap
		if (Math.sqrt(vx*vx+vy*vy)>2.82){
			vx = curvx;
			vy = curvy;
		}
		
		//has stopped
		if (Math.abs(vx)<0.1 && Math.abs(vy)<0.1) {
			power=0.75;
			vx=0;
			vy=0;
		}
		
		xpos += vx;
		ypos += vy;
		//System.out.println(vx+ " "+ vy);
	}
	
	public void moveforward(){
		power+=accelrate;
		curvx = vx;
		curvy = vy;
		vx = cos(angle) * power;
		vy = sin(angle) * power;
		if (power>pcap)power=pcap;
		
	}
	
	public double sin(double deg){
		return Math.sin(Math.toRadians(deg));
	}
	public double cos(double deg){
		return Math.cos(Math.toRadians(deg));
	}
	
	public void stop(){
		velocity=0.5;
		vx= cos(angle)*velocity;
		vy= sin(angle)*velocity;
		power=1;
	}
	
	public void restart(){
		power=0.75;
	}
	
	public void loselife(){
		life--;
		if (life<=0){
			active=false;
		}
	}
	public int getlife(){
		return life;
	}
	
	public void turnclock(){
		angle+=turnSpeed;
	}
	public void turncounterclock(){
		angle-=turnSpeed;
	}
	public void rotate(){
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(angle), orig.getWidth()/2, orig.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		picture = op.filter(orig, null);
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




