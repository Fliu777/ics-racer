/*
Frank Liu & Michael Zhang
Player Class
Physics Racing Game
ICS4U
 */

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PlayerCar extends GameObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ign;
	private double vx;
	private double vy;
	private double fric;
	private double turnSpeed;
	private double power;
	private int life = 10;
	private double pcap;
	private double pstart;
	double curvx, curvy;
	double accel;
	private int indexcar;
	private double velact;
	
	private double abposx, abposy;

	public PlayerCar() {
		super();
		
		//player statistics
		
		
		
		
		velocity = 0;
		angle = 0;
		pcap = 20;
		pstart = 2;
		indexcar = 0;
		turnSpeed =2.5;
		fric = 0.90;
		power = pstart;
		vx = cos(angle) * velocity;
		vy = sin(angle) * velocity;
		curvx = vx;
		curvy = vy;
		
		xpos=abposx=650;
		ypos=abposy=475;
		velact=Math.sqrt(vx * vx + vy * vy);

	}

	public void draw(Graphics g) {
		orig = GamePanel.cars[indexcar];
		rotate();
		super.draw(g);
		orig = null;
		g.drawLine((int) xpos, (int) ypos,
				(int) (xpos + Math.cos(Math.toRadians(angle)) * 25),
				(int) (ypos + Math.sin(Math.toRadians(angle)) * 25));
	}
	
	public void drawrel(Graphics g, PlayerCar other) {
		//get the drawing relative to the car
		orig = GamePanel.cars[indexcar];
		rotate();
		g.drawImage(picture, (int)(this.abposx-other.abposx+other.xpos), (int)(this.abposy-other.abposy+other.ypos), null);
		orig = null;
		
	}


	public void move(GameMap map) {
		//apply friction
		vx *= fric;
		vy *= fric;

		// Velocity cap

		if (velact> pcap) {
			vx = curvx;
			vy = curvy;
		}
		turnSpeed=velact/3;

		// has stopped
		if (Math.abs(vx) < 0.1 && Math.abs(vy) < 0.1) {
			power = pstart;
			vx = 0;
			vy = 0;
		}
		
		//reversed direction if bump
		if (GamePanel.map.callOnRoad()==false) {
			vx=-vx;
			vy=-vy;
			vx/=2;
			vy/=2;
		}

		//same as above
		if (!(xpos+vx<5 ||xpos+vx>MainLoop.ScreenWidth-5 ||ypos+vy<5 ||ypos+vy>MainLoop.ScreenHeight-5)){
			xpos += vx;
			ypos += vy;
			
			abposx+=vx;
			abposy+=vy;
		}
		else{
			vx=-vx;
			vy=-vy;
			vx/=1.5;
			vy/=1.5;
		}

	}

	public void moveforward() {
		//incrase amount of vx/vy
		vx += cos(angle) * power;
		vy += sin(angle) * power;
		velact=Math.sqrt(vx * vx + vy * vy);

		if (velact < pcap){
			curvx = vx;
			curvy = vy;
		}
	}

	public void movebackward() {
		//backward
		vx -= cos(angle) * power;
		vy -= sin(angle) * power;
		velact=Math.sqrt(vx * vx + vy * vy);

		if (velact <- pcap){
			curvx = vx;
			curvy = vy;
		}
	}
	
	public double getvelocity(){
		return Math.sqrt(vx * vx + vy * vy);
	}

	public double sin(double deg) {
		return Math.sin(Math.toRadians(deg));
	}

	public double cos(double deg) {
		return Math.cos(Math.toRadians(deg));
	}

	public void stop() {
		// vx=vy=0;
		power = pstart;
	}

	public void turnclock() {
		angle += turnSpeed;
	}

	public void turncounterclock() {
		angle -= turnSpeed;
	}

	public void restart() {
		power = pstart;
		vx/=1.5;
		vy/=1.5;
	}

	public void loselife() {
		life--;
		if (life <= 0) {
			active = false;
		}
	}

	public int getlife() {
		return life;
	}

	public void rotate() {
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(angle), orig.getWidth() / 2,
				orig.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		picture = op.filter(orig, null);
	}
	
	public double getsizex(){
		return xpos+picture.getWidth()/2+vx;
	}
	public double getsizey(){
		return ypos+picture.getHeight()/2+vy;
	}

	public void moveright() {
		xpos += velocity;
	}

	public void moveleft() {
		xpos -= velocity;
	}

	public void moveup() {
		ypos -= velocity;
	}

	public void movedown() {
		ypos += velocity;
	}

	public String toString() {
		return "The velocity " + vx + vy + "the angle" + angle;
	}

	public double getVX() {
		return curvx;
	}

	public double getVY() {
		return curvy;
	}

	// more look
	private void writeObject(ObjectOutputStream out) throws IOException {
		//System.out.println("wruite  COUNT IS :::");
		out.defaultWriteObject();
		// ImageIO.write(picture, "png", out); // png is lossless
		// ImageIO.write(orig, "png", out); // png is lossless
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
	//	System.out.println("read  COUNT IS :::");

		in.defaultReadObject();
		// final int imageCount = in.readInt();
		// System.out.println("IMAGE  COUNT IS "+imageCount);
		// picture=ImageIO.read(in);
		// orig=ImageIO.read(in);

	}

}