package preprocess;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class SentenceSplitterAndTokenizer {
	static final String filename = "/data/RCV1/final/corpus_clean.txt";

	public static void main(String[] args) throws IOException {
		PrintWriter pw = new PrintWriter(filename + ".tokenized.SPL");
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PTBTokenizer tokenizer = new PTBTokenizer(new FileReader(filename),
				new CoreLabelTokenFactory(), "");
		Sentence s = new Sentence();
		for (CoreLabel label; tokenizer.hasNext();) {
			label = (CoreLabel) tokenizer.next();
			String token = label.toString("value");
			if(token.equals("-LRB-")) {
				token = "(";
			}
			if(token.equals("-RRB-")) {
				token = ")";
			}
			if(token.equals("-LSB-")) {
				token = "[";
			}
			if(token.equals("-RSB-")) {
				token = "]";
			}
			if(token.equals("-LCB-")) {
				token = "{";
			}
			if(token.equals("-RCB-")) {
				token = "}";
			}
			s.words.add(token);
			// is it sentence splitter?
			if (token.equals(".") || token.equals("?") || token.equals("!")) {
				if(s.isCleanSentence()) {
					pw.println(s.toString());					
				} 
				s = new Sentence();
			}
		}
		pw.close();
	}
}
