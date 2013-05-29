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
	private BufferedImage[] roadArray;
	
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
		
		//Road image array
		BufferedImage[] roadArrayTemp = {mapNS, mapEW, mapNE, mapSE, mapSW, mapNW, map4Way, mapOR};
		roadArray = new BufferedImage[8];
		for (int i = 0; i<8; i++) roadArray[i] = roadArrayTemp[i];
		
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
	
	public String[][] currentMap(int x, int y) {
		String[][] newMap = new String[7][7];
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				newMap[i][j] = textMap.get(x+i).substring(y+j,y+j+1);
			}
		}
		return newMap;
	}
	
	
	
	public void draw(Graphics g) {
		String[][] map = currentMap(0,0);
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				if (map[i][j].equals(".")) {
					g.drawImage(roadArray[7],i*200, j*200, null);
				}
				else {
					g.drawImage(roadArray[3], i*200, j*200, null);
				}
			}
		}
	}
}
