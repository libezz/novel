package tool;

import java.util.ArrayList;

public class NovelTool {
	
	private static final String TEXTAREA_HTML = "<textarea row='3' id='text' placeholder='请勿使用&/-/空格，否则你的评论将不能完整显示'></textarea>";
	private static final String LEFT_SPAN = "<span id='submit' onclick='addMessage(\"";
	private static final String MID_SPAN = "\", \"";
	private static final String RIGHT_SPAN = "\")'>提交</span>";

	private static String[] getInfo(String line) {
		return line.split("-");
	}
	
	public static String getNovelLocation(String line) {
		return getInfo(line)[0];
	}
	
	public static String getNovelName(String line) {
		return getInfo(line)[1];
	}
	
	public static String getChapterLocation(String line) {
		return getInfo(line)[0];
	}
	
	public static String getChapterNum(String line) {
		return getInfo(line)[1];
	}
	
	public static String getChapterName(String line) {
		return getInfo(line)[2];
	}
	
	public static String getMessageNovel(String line) {
		return getInfo(line)[0];
	}
	
	public static String getMessageChapter(String line) {
		return getInfo(line)[1];
	}
	
	public static String getMessageContent(String line) {
		return getInfo(line)[2];
	}
	
	public static String getLineByLocation(String location, ArrayList<String> lines) {
		for(int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if(line.startsWith(location)) {
				return line;
			}
		}
		return "-------";
	}
	
	public static int getCountByLocation(String location, ArrayList<String> lines) {
		for(int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if(line.startsWith(location)) {
				return i;
			}
		}
		return -1;
	}
	
	private static ArrayList<ArrayList<String>> classifyLines(String location, ArrayList<String> lines) {
		ArrayList<String> inLines = new ArrayList<String>();
		ArrayList<String> outLines = new ArrayList<String>();
		for(int i = lines.size(); i > 0; i--) {
			String line = lines.get(i - 1);
			if(line.startsWith(location)) {
				inLines.add(line);
			} else {
				outLines.add(line);
			}
		}
		ArrayList<ArrayList<String>> linesArray = new ArrayList<ArrayList<String>>();
		linesArray.add(inLines);
		linesArray.add(outLines);
		return linesArray;
	}
	
	public static ArrayList<String> getInLinesByLocation(String location, ArrayList<String> lines) {
		return classifyLines(location, lines).get(0);
	}
	
	public static ArrayList<String> getOutLinesByLocation(String location, ArrayList<String> lines) {
		return classifyLines(location, lines).get(1);
	}
	
	public static String setTextarea (String chapter, String novel) {
		return TEXTAREA_HTML + LEFT_SPAN + chapter + MID_SPAN + novel + RIGHT_SPAN;
	}
}
