import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class GameMap {
	
	private ArrayList<File> fileMapArray; //Used for reading multiple map level designs
	private FileReader fileMapStream;
	private BufferedReader mapData;
	private ArrayList<String> textMap; //ArrayList to hold content of each map text file for any size
	private BufferedImage mapNS, mapEW, mapNE, mapSE, mapSW, mapNW, mapOR, map4Way; //Image block variables
	private BufferedImage[] roadArray;
	private boolean up,down,left,right; //Used for block detection in recursive pathing
	private String[][] textArray;
	
	public GameMap() {
		
		//Try/Catch used for image reading
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
		
		//Reading current level
		try {
			fileMapStream = new FileReader(fileMapArray.get(0));
			mapData = new BufferedReader(fileMapStream);
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
		}
		
		//Text map arraylist initated and populated
		textMap = new ArrayList<String>();
		try {
			String line;
			while ((line = mapData.readLine()) != null) textMap.add(line);
			mapData.close();
			fileMapStream.close();
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
		}
		
		//Arraylist is transferred onto a 2D array for easier use
		textArray = new String[textMap.size()][textMap.get(0).length()];
		for (int i = 0; i<textArray.length; i++) {
			for (int j = 0; j<textArray[0].length; j++) textArray[i][j] = textMap.get(i).substring(j,j+1);
		}
	}
	
	public String[][] currentMap(int x, int y) {
		String[][] newMap = new String[7][7];
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				newMap[i][j] = textArray[x+i][y+j];
			}
		}
		return newMap;
	}
	
	//Recursive path finding method and map conversion for easier reading
	public void mapConvert(int x, int y) {
		up=down=left=right=false; //Sets all surrounding blocks as false (no path)
		
		//If a path is detected, set variable to true
		if (!(textArray[x+1][y].equals("."))) down=true;
		if (!(textArray[x-1][y].equals("."))) up=true;
		if (!(textArray[x][y+1].equals("."))) right=true;
		if (!(textArray[x][y-1].equals("."))) left=true;
		
		//Change the character of the current block after judging which direction the block is going in
		if (up&&down) textArray[x][y] = "|"; //NS block
		else if (left&right) textArray[x][y] = "-"; //SW block
		else if (up&&right) textArray[x][y] = "1"; //1 is NE
		else if (down&&right) textArray[x][y] = "2"; //2 is SE
		else if (down&&left) textArray[x][y] = "3"; //3 is SW
		else if (up&&left) textArray[x][y] = "4"; //4 is NW
		
		//Base case return if all 4 sides do not have modifiable paths
		if (!(textArray[x-1][y].equals("X"))&&!(textArray[x+1][y].equals("X"))&&!(textArray[x][y-1].equals("X"))&&!(textArray[x][y+1].equals("X"))) return;
		
		//If an unmodified ("X") block is found on any of the 4 adjacent sides, call upon the method again to the new side
		if (textArray[x-1][y].equals("X")) mapConvert(x-1,y);
		if (textArray[x+1][y].equals("X")) mapConvert(x+1,y);
		if (textArray[x][y-1].equals("X")) mapConvert(x,y-1);
		if (textArray[x][y+1].equals("X")) mapConvert(x,y+1);
	}
	
	public void draw(Graphics g) {
		mapConvert(2,3);
		String[][] map = currentMap(0,0);
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				if (map[i][j].equals(".")) g.drawImage(roadArray[7],i*200, j*200, null);
				else if (map[i][j].equals("-")) g.drawImage(roadArray[0], i*200, j*200, null);
				else if (map[i][j].equals("|")) g.drawImage(roadArray[1], i*200, j*200, null);
				else if (map[i][j].equals("1")) g.drawImage(roadArray[2], i*200, j*200, null);
				else if (map[i][j].equals("2")) g.drawImage(roadArray[3], i*200, j*200, null);
				else if (map[i][j].equals("3")) g.drawImage(roadArray[4], i*200, j*200, null);
				else if (map[i][j].equals("4")) g.drawImage(roadArray[5], i*200, j*200, null);
			}
		}
	}
}
