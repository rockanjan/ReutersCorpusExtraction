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

public class CleanNERData {
	public static void main(String[] args) throws SAXException, IOException {
		Date startTime = new Date(); 
		System.out.println("Start Time = " + startTime);
		String filename = "/home/anjan/workspace/HMM/data/combined.txt.SPL";
		
		Corpus corpus = new Corpus();
		corpus.readTextFromFile(filename);
		String outFile = filename + ".clean";
		PrintWriter pw = new PrintWriter(outFile);
		pw.print(corpus.getRawText());
		pw.close();
		System.out.println("File written at : " + outFile);
		Date endTime= new Date();
		System.out.println("EndTime = " + endTime);
		System.out.println("Total Time taken : " + ( (endTime.getTime() - startTime.getTime())/1000/60) + " minutes" ); 
	}

}
