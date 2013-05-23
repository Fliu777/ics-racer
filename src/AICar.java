
public class AICar extends PlayerCar{
	private int counter=500;
	public AICar(){
		super();
	}
	public void update(){
		counter++;
		moveforward();
		if (xpos>MainLoop.ScreenWidth || xpos<0 ||  ypos>MainLoop.ScreenHeight || ypos<0){
			counter=0;
		}
		if (counter<90){
			turnclock();
		}
	}
	
}
