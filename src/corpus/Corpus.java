package corpus;

import java.util.ArrayList;
import java.util.List;

public class Corpus {
	public List<Text> texts;
	
	public Corpus() {
		texts = new ArrayList<Text>();
	}
	
	public String getRawText() {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<texts.size(); i++) {
			sb.append(texts.get(i).getRawText());
			sb.append("\n");
		}
		return sb.toString();
	}
}
