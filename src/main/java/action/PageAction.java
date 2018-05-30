package action;

import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tool.FileTool;
import tool.HttpTool;
import tool.NovelTool;

@Controller
public class PageAction {

	@RequestMapping("/")
	public ModelAndView getIndex() {
		if(HttpTool.isMobileDevice()) {
			return new ModelAndView("/page/mobileIndex.html");
		} else {
			return new ModelAndView("/page/desktopIndex.html");
		}
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public String getTest() {
		ArrayList<String> lines = FileTool.fileToLine("test.txt");
		return lines.get(0);
	}
	
	@RequestMapping("/testW")
	@ResponseBody
	public String getResult(String xxx) {
		FileTool.addLine("novel/test.txt", xxx);
		return "yes";
	}
	
	@RequestMapping("/getNovelList")
	@ResponseBody
	public String getNovelList() {
		StringBuilder result = new StringBuilder("<h1>选择</h1>");
		ArrayList<String> lines = FileTool.fileToLine("novellist.txt");
		for(int i = lines.size(); i > 0; i--) {
			String line = lines.get(i - 1);
			result.append("<p><span onclick='getChapterList(\"");
			result.append(NovelTool.getNovelLocation(line));
			result.append("\")'>");
			result.append(NovelTool.getNovelName(line));
			result.append("</span></p>");
		}
		return result.toString();
	}
	
	@RequestMapping("/getChapterList")
	@ResponseBody
	public String getChapterList(String novel) {
		StringBuilder result = new StringBuilder("<h1>选择章节</h1>");
		ArrayList<String> lines = FileTool.fileToLine(novel + ".txt");
		for(int i = lines.size(); i > 0; i--) {
			String line = lines.get(i - 1);
			result.append("<p><span onclick='getChapter(\"");
			result.append(NovelTool.getChapterLocation(line));
			result.append("\", \"" + novel + "\")'>");
			result.append(NovelTool.getChapterNum(line));
			result.append("&emsp;");
			result.append(NovelTool.getChapterName(line));
			result.append("</span></p>");
		}
		return result.toString();
	}
	
	@RequestMapping("/getChapterContent")
	@ResponseBody
	public String getChapterContent(String novel, String chapter) {
		StringBuilder result = new StringBuilder("<h1>");
		ArrayList<String> lines = FileTool.fileToLine(novel + ".txt");
		String line = NovelTool.getLineByLocation(chapter, lines);
		result.append(NovelTool.getChapterNum(line));
		result.append("</h1>");
		ArrayList<String> content = FileTool.fileToLine(novel + "/" + chapter + ".txt");
		for(int i = 0; i < content.size(); i++) {
			String para = content.get(i);
			result.append("<p>");
			result.append(para);
			result.append("</p>");
		}
		return result.toString();
	}
	
	@RequestMapping("/getNovelName")
	@ResponseBody
	public String getNovelName(String novel) {
		StringBuilder result = new StringBuilder("");
		ArrayList<String> lines = FileTool.fileToLine("novellist.txt");
		String line = NovelTool.getLineByLocation(novel, lines);
		result.append(NovelTool.getNovelName(line));
		return result.toString();
	}
	
	@RequestMapping("/getChapterName")
	@ResponseBody
	public String getChapterName(String novel, String chapter) {
		StringBuilder result = new StringBuilder("");
		ArrayList<String> lines = FileTool.fileToLine(novel + ".txt");
		String line = NovelTool.getLineByLocation(chapter, lines);
		result.append(NovelTool.getChapterNum(line));
		result.append("&emsp;");
		result.append(NovelTool.getChapterName(line));
		return result.toString();
	}
	
	@RequestMapping("/getOtherChapter")
	@ResponseBody
	public String getOtherChapter(String novel, String chapter) {
		StringBuilder result = new StringBuilder("");
		ArrayList<String> lines = FileTool.fileToLine(novel + ".txt");
		int count = NovelTool.getCountByLocation(chapter, lines);
		int fromCount = count - 4;
		if(fromCount < 0) {
			fromCount = 0;
		}
		int toCount = count + 5;
		if(toCount > lines.size()) {
			toCount = lines.size();
		}
		for(int i = fromCount; i < toCount; i++) {
			String line = lines.get(i);
			result.append("<p><span onclick='getChapter(\"");
			result.append(NovelTool.getChapterLocation(line));
			result.append("\", \"" + novel + "\")'>");
			result.append(NovelTool.getChapterNum(line));
			result.append("&emsp;");
			result.append(NovelTool.getChapterName(line));
			result.append("</span></p>");
		}
		return result.toString();
	}
	
	@RequestMapping("/getMessage")
	@ResponseBody
	public String getMessage(String novel, String chapter) {
		StringBuilder result = new StringBuilder("");
		ArrayList<String> lines = FileTool.fileToLine("message.txt");
		String location = novel + "-" + chapter;
		ArrayList<String> inLines = NovelTool.getInLinesByLocation(location, lines);
		if(inLines.size() == 0) {
			result.append("<p>暂无</p>");
		} else {
			for(int i = 0; i < inLines.size(); i++) {
				String inLine = inLines.get(i);
				result.append("<p>");
				result.append(NovelTool.getMessageContent(inLine));
				result.append("</p>");
			}
		}
		result.append(NovelTool.setTextarea(chapter, novel));
		return result.toString();
	}
	
	@RequestMapping("/getOtherMessage")
	@ResponseBody
	public String getOtherMessage(String novel, String chapter) {
		StringBuilder result = new StringBuilder("");
		ArrayList<String> lines = FileTool.fileToLine("message.txt");
		String location = novel + "-" + chapter;
		ArrayList<String> outLines = NovelTool.getOutLinesByLocation(location, lines);
		int count = outLines.size();
		if(count == 0) {
			result.append("<p>暂无</p>");
		} else {
			if(count > 10) {
				count = 10;
			}
			for(int i = 0; i < count; i++) {
				String outLine = outLines.get(i);
				result.append("<p><span onclick='getChapter(\"");
				result.append(NovelTool.getMessageChapter(outLine));
				result.append("\", \"");
				result.append(NovelTool.getMessageNovel(outLine));
				result.append("\")'>");
				result.append(NovelTool.getMessageContent(outLine));
				result.append("</span></p>");
			}
		}
		return result.toString();
	}
	
	@RequestMapping("/addMessage")
	@ResponseBody
	public String addMessage(String novel, String chapter, String text) {
		if(text == null || "".equals(text)) {
			return "";
		}
		text = FileTool.inputFormat(text);
		FileTool.addLine("message.txt", novel + "-" + chapter + "-" + text);
		return "";
	}
}
