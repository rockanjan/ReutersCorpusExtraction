package corpus;

public class Paragraph {
	private String rawText;
	public static boolean debug = false;
	
	public Paragraph() {
		
	}
	
	public void setRawText(String rawText) {
		this.rawText = rawText.trim();
	}
	
	public String getRawText() {
		return rawText;
	}
	
	public String getCleanText() {
		//TODO: cleaning code
		String cleanText = rawText;
		
		return cleanText;
	}
	
	//Percy Liang's technique (in his thesis, page 51), for filtering sentences 
	//(a paragraph should contain at least 90% of lowercase characters and white space)
	public boolean resemblesRealSentence() {
		double THRESHOLD = 0.9;
		int allCount = rawText.length();
		int lowercaseCount = 0;
		for(int i=0; i<allCount; i++) {
			char c = rawText.charAt(i);
			if(c ==' ' || (c >= 'a' && c <= 'z')) {
				lowercaseCount++;
			}
		}
		double fraction = 1.0 * lowercaseCount / allCount;
		if(debug) {
			System.out.println(rawText);
			System.out.println("fraction = " + fraction);
		}
		if(fraction >= THRESHOLD) {
			return true;
		}
		return false;
	}
	
	public boolean resemblesTableHeaders() {
		int THRESHOLD = 5;
		//simple heuristic: if numberOfTokens/numberOfLines < THRESHOLD, it resembles table headers
		System.out.println(rawText);
		int numberOfTokens = rawText.split("(\n|\\s+)").length;
		int numberOfLines = rawText.split("\n").length;
		//System.out.format("#tokens = %d, #lines = %d\n", numberOfTokens, numberOfLines);
		if(numberOfLines == 0) {
			return false;
		}
		if( 1.0 * numberOfTokens / numberOfLines < THRESHOLD) {
			System.out.println("HEADER:");
			System.out.println(rawText);
			return true;
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		Paragraph.debug = true;
		Paragraph p = new Paragraph();
		//p.rawText = "composite index of 500 stocks fell from 0.89 to 665.69";
		p.rawText = "\"The focus is back on Mexican fundamentals,\" " +
				"said Lars Schonander, head of researcher at Santander in Mexico City. " +
				"\"You have a continuing decline in inflation, a stronger-than-expected GDP growth figure " +
				"and the lack of any upward move in U.S. rates.\"";
		
		p.rawText = "Thi is \n a test";
		//System.out.println(p.resemblesRealSentence());
		p.resemblesTableHeaders();
	}
}
