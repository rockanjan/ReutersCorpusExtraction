package corpus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Corpus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1630492275470489841L;
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
