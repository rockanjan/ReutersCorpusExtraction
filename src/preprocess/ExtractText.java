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

public class ExtractText {
	public static final String REUTER_LOCATION = "/data/REUTERS_CORPUS_1/";
	public static final String TEST_LOCATION = "/data/test/";
	public static String OUT_LOCATION = "/data/out/";
	public static void main(String[] args) throws SAXException, IOException {
		Date startTime = new Date(); 
		System.out.println("Start Time = " + startTime);
		
		Corpus corpus = new Corpus();
		File folder = new File(REUTER_LOCATION);
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
		//if OUT_LOCATION folder does not exist, create it
		File outFolder = new File(OUT_LOCATION);
		if( ! outFolder.exists() ) {
			outFolder.mkdir();
		}
		if(outFolder.isFile() || !outFolder.canWrite()) {
			System.out.println("Saving at /tmp");
			OUT_LOCATION = "/tmp/";
		}
		String outFile = OUT_LOCATION + "corpus_clean.txt";
		PrintWriter pw = new PrintWriter(outFile);
		pw.print(corpus.getRawText());
		pw.close();
		System.out.println("File written at : " + outFile);
		Date endTime= new Date();
		System.out.println("EndTime = " + endTime);
		System.out.println("Total Time taken : " + ( (endTime.getTime() - startTime.getTime())/1000/60) + " minutes" ); 
	}

}
