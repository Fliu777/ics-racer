/*
Frank Liu & Michael Zhang
Player Class
Physics Racing Game
ICS4U
*/

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;



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
        private int life=10;
        private double pcap;
        private double pstart;
        double curvx, curvy;
        double accel;
        
        public PlayerCar(){
                super();
                velocity=0;
                angle=0;
                pcap=8.5;
                pstart=1.5;
                
                accel=0.05;
                turnSpeed=0.45;
                fric=0.979;
                power=pstart;
                vx= cos(angle)*velocity;
                vy= sin(angle)*velocity;
        		try {
        			picture= ImageIO.read(new File("src/Images/car1.png"));
        			picture=orig;
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
                
        }
        public void draw(Graphics g){

                
                super.draw(g);
                g.drawLine((int)xpos, (int)ypos, (int)(xpos+Math.cos(Math.toRadians(angle))*25), (int)(ypos+Math.sin(Math.toRadians(angle))*25));
        }
        public void move(){
        	rotate();
                vx *= fric;
                vy *= fric;
        
                //Velocity cap
                if (Math.sqrt(vx*vx+vy*vy)>pcap){
                        vx = curvx;
                        vy = curvy;
                }
                
                //has stopped
                if (Math.abs(vx)<0.1 && Math.abs(vy)<0.1) {
                        power=pstart;
                        vx=0;
                        vy=0;
                }
                
                xpos += vx;
                ypos += vy;
                
        		//temp fix to make the map not small while waiting for scroll
        		//disable when scroll done
        		if (xpos>MainLoop.ScreenWidth || xpos<0 ||  ypos>MainLoop.ScreenHeight || ypos<0){
        			vx=-vx;
        			vy=-vy;
        			angle=180+angle;
        		}
                
              //  System.out.println(vx+ " "+ vy);
        }
        
        public void moveforward(){
                power+=accel;
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
        	vx=vy=0;
            power=pstart;
        }
        
        public void turnclock(){
                angle+=turnSpeed;
        }
        public void turncounterclock(){
                angle-=turnSpeed;
        }
    	public void restart(){
    		power=pstart;
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
        
        public String toString(){
        	return "The velocity "+ vx+ vy+ "the angle"+angle;
        }
        
        //more look
        private void writeObject(ObjectOutputStream out) throws IOException {
            System.out.println("IMAGE  COUNT IS :::");
            out.defaultWriteObject();
            ImageIO.write(picture, "png", out); // png is lossless
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        	in.defaultReadObject();
            final int imageCount = in.readInt();
            System.out.println("IMAGE  COUNT IS "+imageCount);
 
            picture=ImageIO.read(in);
        }

}