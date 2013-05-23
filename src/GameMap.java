import java.awt.*;
import java.io.*;
import java.util.*;

public class GameMap {
	
	private File fileMapRaw;
	private FileReader fileMapStream;
	private BufferedReader mapData;
	private ArrayList<String> textMap;
	
	public GameMap() {
		fileMapRaw = new File("TextMap.txt");
		try {
			fileMapStream = new FileReader(fileMapRaw);
			mapData = new BufferedReader(fileMapStream);
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
		}
		textMap = new ArrayList<String>();
		try {
			String line;
			while (mapData.readLine() != null) {
				line = mapData.readLine();
				for (int i = 0; i<line.length(); i++) textMap.add(line.substring(i,i+1));
			}
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
		}
	}
	
	public void draw(Graphics g) {
		for (int i = 0; i<textMap.size(); i++) {
			
		}
	}
}
