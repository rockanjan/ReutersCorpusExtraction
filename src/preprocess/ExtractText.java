package preprocess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import corpus.Corpus;
import corpus.Text;

public class ExtractText {
	public static final String REUTER_LOCATION = "/data/REUTERS_CORPUS_1/";
	public static final String TEST_LOCATION = "/data/test/";
	public static final String OUT_LOCATION = "/data/out/";
	public static void main(String[] args) throws SAXException, IOException {
		Corpus corpus = new Corpus();
		File folder = new File(TEST_LOCATION);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String file = listOfFiles[i].getName();
				if(file.startsWith("199") && file.endsWith(".zip")) {
					String zipFileName = listOfFiles[i].getAbsolutePath();
					ZipFile zipFile = new ZipFile(zipFileName);
					Enumeration<? extends ZipEntry> entries = zipFile.entries();
					
					while (entries.hasMoreElements()) {
				        ZipEntry zipEntry = entries.nextElement();
				        if (!zipEntry.isDirectory()) {
				            final String fileName = zipEntry.getName();
				            if (fileName.endsWith(".xml")) {
				                InputStream xmlStream = zipFile.getInputStream(zipEntry);
				                ReuterCorpusParser parser = new ReuterCorpusParser(xmlStream);
				                Text text = parser.parse();
								corpus.texts.add(text);
								xmlStream.close();				                
				            }
				        }				        
				    }
				    zipFile.close();				    
				}
			}
		}
		
		//write plain text
		String outFile = OUT_LOCATION + "corpus.txt";
		PrintWriter pw = new PrintWriter(outFile);
		pw.print(corpus.getRawText());
		pw.close();
		System.out.println("File written at : " + outFile);
	}

}
