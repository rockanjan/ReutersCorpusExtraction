package preprocess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/*
 * selects a fixed number of samples from the whole corpus
 */
public class RandomSampleSelection {
	public static void main(String[] args) throws IOException {
		int sampleSize = 1300000;
		String wholeCorpusFile = "/data/RCV1/final/corpus_clean.txt.tokenized.SPL.uniq";
		String sampledCorpusFile = wholeCorpusFile + "." + sampleSize;
		ArrayList<String> sentences = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(wholeCorpusFile));
		String sentence = "";
		int count = 0;
		ArrayList<Integer> integerList = new ArrayList<Integer>();
		while((sentence = br.readLine()) != null) {
			sentence = sentence.trim();
			if(sentence.isEmpty()) continue;
			sentences.add(sentence);
			integerList.add(count++);
		}
		br.close();
		System.out.println("Total Sentences in the whole corpus: " + count);
		//shuffle integers
		Collections.shuffle(integerList);
		PrintWriter pw = new PrintWriter(sampledCorpusFile);
		for(int i=0; i<sampleSize; i++) {
			pw.println(sentences.get(integerList.get(i)));
			pw.flush();
		}
		pw.close();
	}

}
