package preprocess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import corpus.Corpus;
import corpus.Text;

public class ExtractTextGigaword {
	public static final String GIGAWORD_LOCATION = "/data/gigaword/nyt_eng_processed/rootadded/";
	public static final String TEST_LOCATION = "/data/testnyt/";
	public static String OUT_LOCATION = "/data/gigaword/nyt_eng_final/";

	public static void main(String[] args) throws SAXException, IOException {
		Date startTime = new Date();
		System.out.println("Start Time = " + startTime);

		Corpus corpus = new Corpus();
		File folder = new File(TEST_LOCATION);
		File[] listOfFiles = folder.listFiles();
		System.out.println("Total files : " + listOfFiles.length);
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String file = listOfFiles[i].getName();
				if (file.startsWith("nyt")) {
					System.out.println("Parsing file " + i + " " + file);
					ReuterCorpusParser parser = new ReuterCorpusParser(
							new File(folder.getAbsolutePath() + "/" + file));
					Text text = parser.parse();
					//System.out.println("text parsed");
					corpus.texts.add(text);
				}
			}
		}
		System.out.println("Corpus creation completed! Saving to file...");
		// write plain text
		// if OUT_LOCATION folder does not exist, create it
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
