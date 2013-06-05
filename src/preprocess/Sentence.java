package preprocess;

import java.util.ArrayList;
import java.util.List;

public class Sentence{
	List<String> words;
	String cacheString;
	int maxSentenceLengthThreshold = 50;
	public Sentence() {
		words = new ArrayList<String>();
	}
	
	@Override
	public String toString() {
		if(cacheString != null) {
			return cacheString;
		}
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<words.size(); i++) {
			String w = words.get(i);
			sb.append(w);
			if(i != words.size()) {
				sb.append(" ");
			} else {
				sb.append("\n");
			}
		}
		cacheString = sb.toString();
		return cacheString;
	}
	
	public boolean isCleanSentence() {
		boolean isClean = true;
		if(words.size() < 5 || words.size() > maxSentenceLengthThreshold) {
			isClean = false;
		}
		
		boolean hasInvalidQuotation = toString().contains("``") && !toString().contains("''") || !toString().contains("``") && toString().contains("''");  
		if(hasInvalidQuotation) {
			isClean = false;
		}
		
		if(toString().contains("END OF DOCUMENT")) {
			isClean = false;
		}
		return isClean;
	}

}
