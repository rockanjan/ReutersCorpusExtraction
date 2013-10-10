package preprocess;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import corpus.Paragraph;
import corpus.Text;

public class ReuterCorpusParser extends DefaultHandler{	
	public InputStream xmlInputStream; 
	public File file;
	Text text;
	String tmpString = ""; //tmpString from the element
	boolean isParagraphStarted = false;
	public ReuterCorpusParser(InputStream xmlInputStream) {
		this.xmlInputStream = xmlInputStream;
	}
	public ReuterCorpusParser(File file) {
		this.file = file;
	}
	
	@Override
	public void startElement(String uri, String localName, String elementName,
			Attributes attributes) throws SAXException {
		if(elementName.equalsIgnoreCase("text")) {
			text = new Text();
		}
		if(elementName.equalsIgnoreCase("p")) { //paragraph
			isParagraphStarted = true;
		}
		
	}

	@Override
	public void endElement(String uri, String localName, String elementName)
			throws SAXException {
		if(elementName.equals("p")) {
			Paragraph p = new Paragraph();
			p.setRawText(tmpString);
			text.paragraphs.add(p);
			tmpString = "";
			isParagraphStarted = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(isParagraphStarted) {
			tmpString += new String(ch, start, length);
		}
	}
	
	public Text parse() {
		 SAXParserFactory factory = SAXParserFactory.newInstance();
	        try {
	        	SAXParser parser = factory.newSAXParser();
	        	if(file == null) {
	        		parser.parse(xmlInputStream, this);
	        	} else {
	        		parser.parse(file, this);
	        	}
	        	
	            
	        } catch (ParserConfigurationException e) {
	            System.out.println("ParserConfig error");
	            e.printStackTrace();
	        	System.exit(-1);
	        } catch (SAXException e) {
	        	System.out.println("SAXException : xml not well formed");
	        	e.printStackTrace();
	        	System.exit(-1);
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.exit(-1);
	        }
		return text;
	}
}
