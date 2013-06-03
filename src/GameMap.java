/*
Frank Liu & Michael Zhang
Level Reader
Physics Racing Game
ICS4U
*/

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
	private String[][] textArray, map; //Converted map arrays
	private Graphics g;
	private double vx, vy; //Map scrolling
	private int mapX, mapY;
	
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
		
		//Map scrolling
		mapX = mapY = 0;
		vx = vy = 0.0;
		map = currentMap(mapX,mapY);
	}
	
	//Clipping a portion of the map
	 public String[][] currentMap(int x, int y) {
		String[][] newMap = new String[10][10];
		for (int i = 0; i<10; i++) {
			for (int j = 0; j<10; j++) {
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
		if (up&&down&&left&&right) textArray[x][y] = "+"; //4Way block
		else if (up&&down) textArray[x][y] = "|"; //NS block
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
	
	//Finds the first piece of road
	public int findX(int coord) {
		for (int i = 0; i<textArray.length; i++) {
			for (int j = 0; j<textArray[0].length; j++) {
				if (textArray[i][j].equals("X")&&coord==0) return i;
				else if (textArray[i][j].equals("X")&&coord==1) return j;
			}
		}
		return 0;
	}
	
	//Drawing the current map
	public void drawCurrentMap(String[][] map, double vx, double vy) {
		for (int i = 0; i<map.length; i++) {
			for (int j = 0; j<map[0].length; j++) {
				if (map[i][j].equals("|")) g.drawImage(roadArray[0], (int)Math.round(j*200-vx), (int)Math.round(i*200-vy), null);
				else if (map[i][j].equals("-")) g.drawImage(roadArray[1], (int)Math.round(j*200-vx), (int)Math.round(i*200-vy), null);
				else if (map[i][j].equals("1")) g.drawImage(roadArray[2], (int)Math.round(j*200-vx), (int)Math.round(i*200-vy), null);
				else if (map[i][j].equals("2")) g.drawImage(roadArray[3], (int)Math.round(j*200-vx), (int)Math.round(i*200-vy), null);
				else if (map[i][j].equals("3")) g.drawImage(roadArray[4], (int)Math.round(j*200-vx), (int)Math.round(i*200-vy), null);
				else if (map[i][j].equals("4")) g.drawImage(roadArray[5], (int)Math.round(j*200-vx), (int)Math.round(i*200-vy), null);
				else if (map[i][j].equals("+")) g.drawImage(roadArray[6], (int)Math.round(j*200-vx), (int)Math.round(i*200-vy), null);
				else if (map[i][j].equals(".")) g.drawImage(roadArray[7], (int)Math.round(j*200-vx), (int)Math.round(i*200-vy), null);
			}
		}
	}
	
	public void draw(Graphics g) {
		this.g = g;
		vx++;
		vy++;
		if (vx>200) {
			vx=0;
			mapX++;
		}
		if (vy>200) {
			vy=0;
			mapY++;
		}
		mapConvert(findX(0),findX(1));
		String[][] map = currentMap(mapX,mapY);
		drawCurrentMap(map, vx, vy);
	}
}
