package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import corpus.Corpus;
import corpus.Text;

public class ParseUsingStaX {
	public static final String GIGAWORD_LOCATION = "/data/gigaword/nyt_eng_processed/rootadded/singleline/";
	public static final String TEST_LOCATION = "/data/testnyt/";
	public static String OUT_LOCATION = "/data/gigaword/nyt_eng_final/";

	public static void main(String args[]) throws FileNotFoundException {
		Date startTime = new Date();
		System.out.println("Start Time = " + startTime);

		Corpus corpus = new Corpus();
		File folder = new File(GIGAWORD_LOCATION);
		File[] listOfFiles = folder.listFiles();
		System.out.println("Total files : " + listOfFiles.length);
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String file = listOfFiles[i].getName();
				if (file.startsWith("nyt")) {
					System.out.println("Parsing file " + i + " " + file);
					StaXParser parser = new StaXParser();
					List<Text> textList = parser.readConfig(folder.getAbsolutePath() + "/" + file);
					for(Text text : textList) {
						corpus.texts.add(text);
					}
				}
			}
		}
		File outFolder = new File(OUT_LOCATION);
		if (!outFolder.exists()) {
			outFolder.mkdir();
		}
		if (outFolder.isFile() || !outFolder.canWrite()) {
			System.out.println("Saving at /tmp");
			OUT_LOCATION = "/tmp/";
		}
		String outFile = OUT_LOCATION + "corpus_clean.txt";
		PrintWriter pw = new PrintWriter(outFile);
		pw.print(corpus.getRawText());
		pw.close();
		System.out.println("File written at : " + outFile);
		Date endTime = new Date();
		System.out.println("EndTime = " + endTime);
		System.out.println("Total Time taken : "
				+ ((endTime.getTime() - startTime.getTime()) / 1000 / 60)
				+ " minutes");
	}
}
