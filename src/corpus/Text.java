package corpus;

import java.util.List;
import java.util.ArrayList;

public class Text {
	public List<Paragraph> paragraphs;
	
	public Text() {
		paragraphs = new ArrayList<Paragraph>();
	}
	
	public String getRawText() {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<paragraphs.size(); i++) {
			sb.append(paragraphs.get(i).rawText);
			sb.append("\n");
		}
		return sb.toString();
	}
}
