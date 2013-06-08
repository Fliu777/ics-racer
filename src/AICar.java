/*
Frank Liu & Michael Zhang
AI
Physics Racing Game
ICS4U
*/


public class AICar extends PlayerCar{
	private int counter=500;
	public AICar(){
		super();
	}
	public void update(){
		counter++;

		if (xpos>MainLoop.ScreenWidth-150 || xpos<150 ||  ypos>MainLoop.ScreenHeight-150 || ypos<150){
			counter=0;
		}
		if (counter<150){
			turnclock();
		}
		moveforward();
		//move();
	}
	
	public void follow(PlayerCar temp){
		
	}
	
}
