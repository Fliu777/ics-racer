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
	private boolean up,down,left,right;
	private String[][] textArray;
	
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
		textArray = new String[textMap.get(0).length()][textMap.size()];
		for (int i = 0; i<textArray.length; i++) {
			for (int j = 0; j<textArray[0].length; j++) {
				textArray[i][j] = textMap.get(j).substring(i,i+1);
			}
		}
		width = MainLoop.ScreenWidth;
		height = MainLoop.ScreenHeight-30;
		tempHeight = textMap.size();
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
	
	public void mapConvert(int x, int y) {
		up=down=left=right=false;
		if (!(textArray[x+1][y].equals("."))) down=true;
		if (!(textArray[x-1][y].equals("."))) up=true;
		if (!(textArray[x][y+1].equals("."))) right=true;
		if (!(textArray[x][y-1].equals("."))) left=true;
		//base case return if all 4 sides do not have modifiable paths
		if (!(textArray[x-1][y].equals("X"))&&!(textArray[x+1][y].equals("X"))&&!(textArray[x][y-1].equals("X"))&&!(textArray[x][y+1].equals("X"))) return;
		if (up&&down) textArray[x][y] = "|";
		else if (left&right) textArray[x][y] = "-";
		else if (up&&right) textArray[x][y] = "1";
		else if (down&&right) textArray[x][y] = "2";
		else if (down&&left) textArray[x][y] = "3";
		else if (up&&left) textArray[x][y] = "4";
		if (textArray[x-1][y].equals("X")) mapConvert(x-1,y);
		if (textArray[x+1][y].equals("X")) mapConvert(x+1,y);
		if (textArray[x][y-1].equals("X")) mapConvert(x,y-1);
		if (textArray[x][y+1].equals("X")) mapConvert(x,y+1);
	}
	
	public void draw(Graphics g) {
		mapConvert(2,3);
		for (int i = 0; i<textArray.length; i++) {
			for (int j = 0; j<textArray[0].length; j++) {
				System.out.print(textArray[i][j]);
			}
			System.out.println();
		}
		System.out.println("\n\n\n\n\n\n\nQOOOOOo");
		String[][] map = currentMap(0,0);
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				System.out.print(map[i][j]);
				if (map[i][j].equals(".")) {
					g.drawImage(roadArray[7],i*200, j*200, null);
				}
				else if (map[i][j].equals("-")){
					//g.drawImage(roadArray[0], i*200, j*200, null);
				}
			}
			System.out.println();
		}
		System.out.println("SDSADS\n\n\n\n\n\n");
	}
}
