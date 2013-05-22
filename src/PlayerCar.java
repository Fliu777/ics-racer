import java.awt.Graphics;


public class PlayerCar extends GameObject {
	private String ign;
	private double vx;
	private double vy;
	private double fric;
	private double turnSpeed;
	private double power;
	
	private double pcap;
	double curvx, curvy;
	
	public PlayerCar(){
		super();
		velocity=0;
		angle=0;
		pcap=2.5;
		
		
		turnSpeed=0.45;
		fric=0.989;
		power=0.75;
		vx= cos(angle)*velocity;
		vy= sin(angle)*velocity;
		
	}
	public void draw(Graphics g){

		
		super.draw(g);
		g.drawLine((int)xpos, (int)ypos, (int)(xpos+Math.cos(Math.toRadians(angle))*25), (int)(ypos+Math.sin(Math.toRadians(angle))*25));
	}
	public void move(){
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
		
		System.out.println(vx+ " "+ vy);
	}
	
	public void moveforward(){
		power+=0.005;
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
		vx= Math.cos(Math.toRadians(angle))*velocity;
		vy= Math.sin(Math.toRadians(angle))*velocity;
		power=1;
	}
	
	public void turnclock(){
		angle+=turnSpeed;
	}
	public void turncounterclock(){
		angle-=turnSpeed;
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




