import java.io.*;

public class GameMap {
	
	private File fileMapRaw;
	private FileReader fileMapStream;
	private BufferedReader mapData;
	private char[] mapArray;
	
	public GameMap() {
		fileMapRaw = new File("TextMap.txt");
		try {
			fileMapStream = new FileReader(fileMapRaw);
			mapData = new BufferedReader(fileMapStream);
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
		}
	}
}
