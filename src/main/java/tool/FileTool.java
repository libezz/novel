package tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileTool {

	private static final String PRE_PATH = "c:/novel/";
	
	private static File pathToFile(String path) {
		return new File(PRE_PATH + path);
	}
	
	public static ArrayList<String> fileToLine(String path) {
		File file = pathToFile(path);
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return lines;
	}
	
	public static boolean addLine(String path, String line) {
		File file = pathToFile(path);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(line);
			bw.newLine();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String inputFormat(String text) {
		text = text.replaceAll(" ", "");
		text = text.replaceAll("<", "&lt;");
		return text;
	}
}
