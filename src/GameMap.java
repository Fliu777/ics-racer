import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class GameMap {
	
	private ArrayList<File> fileMapArray;
	private FileReader fileMapStream;
	private BufferedReader mapData;
	private ArrayList<String> textMap;
	private double width, height;
	private int tempHeight;
	private BufferedImage mapNS, mapEW, mapNE, mapSE, mapSW, mapNW, mapOR, map4Way;
	
	public GameMap() {
		
		try {
			mapNS = ImageIO.read(new File("src/Images/roadNS.png"));
			mapEW = ImageIO.read(new File("src/Images/roadEW.png"));
			mapNE = ImageIO.read(new File("src/Images/roadNE.png"));
			mapSE = ImageIO.read(new File("src/Images/roadSE.png"));
			mapSW = ImageIO.read(new File("src/Images/roadSW.png"));
			mapNW = ImageIO.read(new File("src/Images/roadNW.png"));
			mapOR = ImageIO.read(new File("src/Images/roadPLAZA.png"));
			map4Way = ImageIO.read(new File("src/Images/roadNEWS.png"));
			
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
		}
		
		//Adding new Levels
		fileMapArray = new ArrayList<File>();
		fileMapArray.add(new File("src/Levels/TextMap")); 
		
		try {
			fileMapStream = new FileReader(fileMapArray.get(0));
			mapData = new BufferedReader(fileMapStream);
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
		}
		textMap = new ArrayList<String>();
		try {
			String line;
			while ((line = mapData.readLine()) != null) textMap.add(line);
			mapData.close();
			fileMapStream.close();
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
		}
		width = MainLoop.ScreenWidth;
		height = MainLoop.ScreenHeight-30;
		tempHeight = textMap.size();
	}
	
	/*public void showVisibleMap(int x, int y, ) {
		
	}
	*/
	
	
	
	public void draw(Graphics g) {
		g.setColor(Color.gray);
		g.drawImage(mapNW,0,0,null); 
		for (int i = 0; i<textMap.size(); i++) {
			String temp = textMap.get(i);
			int tempWidth = textMap.get(i).length();
			for (int j = 0; j<textMap.get(i).length(); j++) {
				if (temp.substring(j,j+1).equals("X")) {
					g.setColor(Color.gray);
					//g.drawImage(mapNS,(int)Math.round(width/tempWidth*j),(int)Math.round(height/tempHeight*i),null); 
				}
			}
		}
	}
}
