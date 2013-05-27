import java.awt.*;
import java.io.*;
import java.util.*;

public class GameMap {
	
	private ArrayList<File> fileMapArray;
	private FileReader fileMapStream;
	private BufferedReader mapData;
	private ArrayList<String> textMap;
	private double width, height;
	private int tempHeight;
	
	public GameMap() {
		
		//Adding new Levels
		fileMapArray = new ArrayList<File>();
		fileMapArray.add(new File("src/TextMap"));
		
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
	
	public void draw(Graphics g) {
		g.setColor(Color.gray);
		g.fillArc(100, 100, 700, 700, 0, 90);
		for (int i = 0; i<textMap.size(); i++) {
			String temp = textMap.get(i);
			int tempWidth = textMap.get(i).length();
			for (int j = 0; j<textMap.get(i).length(); j++) {
				if (temp.substring(j,j+1).equals("X")) {
					g.setColor(Color.gray);
					//g.fillRect((int)Math.round(width/tempWidth*j),(int)Math.round(height/tempHeight*i), (int)Math.round(width/tempWidth)+1, (int)Math.round(height/tempHeight)+1); 
				}
			}
		}
	}
}
