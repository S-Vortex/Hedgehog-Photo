package se.cth.hedgehogphoto.map;

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;  
import javax.xml.parsers.SAXParser;

/**
 * Complete class taken from the link below.
 * @author 'Geek'
 * @source http://geekexplains.blogspot.se/2009/04/implementation-of-sax-parser-in-java.html
 * @date 2012-03-27
 */
public class XMLParser extends DefaultHandler{

 //Path of the XML File to be parsed - private as we don't want it outside
 //and 'final' as once it's assigned to a value (path), it doesn't require any change
 private static final String XML_FILE_TO_BE_PARSED = "berlin.xml";
 
 //Reference to the output stream
 private static Writer out;    

    public static void main (String argv [])
    {
        //Getting a new instance of the SAX Parser Factory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        
        try {
         
            //Setting up the output stream - in this case System.out with UTF8 encoding
            out = new OutputStreamWriter(System.out, "UTF8");

            //Getting a parser from the factory
            SAXParser saxParser = factory.newSAXParser();
            
            //Parsing the XML document using the parser
            saxParser.parse( new File(XML_FILE_TO_BE_PARSED), new XMLParser() );

        } catch (Throwable throwable) { //Throwable as it can be either Error or Exception
            throwable.printStackTrace ();
        }
        System.exit (0);
    }

    //Implementation of the required methods of the ContentHandler interface
    
    public void startDocument()throws SAXException
    {
        printData("XML File being parsed: " + XML_FILE_TO_BE_PARSED);
        printNewLine();printNewLine();
        printData("INFO: ### Parsing of the XML Doc started ###");
        printNewLine();printNewLine();
        
        printData ("");
        printNewLine();
    }

    public void endDocument()throws SAXException
    {
        try {
             printNewLine();
             printNewLine();
             printData("INFO: ### Parsing of the XML Doc completed ###");
        
             out.flush ();
        } catch(IOException ioe) {
            throw new SAXException ("ERROR: I/O Eexception thrown while parsing XML", ioe);
        }
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts)throws SAXException
    {
        
     printData ("<" + qName);
        
        if (atts != null) {
            for (int i = 0; i < atts.getLength (); i++) {
                printData (" ");
                printData (atts.getQName(i) + "=\"" + atts.getValue(i) + "\"");
            }
        }
        
        printData (">");
    }

    public void endElement(String namespaceURI, String localName, String qName)throws SAXException
    {
        printData ("");
    }

    public void characters(char buffer [], int offset, int length)throws SAXException
    {
        String string = new String(buffer, offset, length);
        printData(string);
    }

    //Definition of helper methods
    
    //printData: accepts a String and prints it on the assigned output stream
    private void printData(String string)throws SAXException
    {
        try {
            
         out.write(string);
            out.flush();
        
        } catch (IOException ioe) {
            throw new SAXException ("ERROR: I/O Exception thrown while printing the data", ioe);
        }
    }

    //printNewLine: prints a new line on the underlying platform
    //end of line character may vary from one platform to another
    private void printNewLine()throws SAXException
    {
        //Getting the line separator of the underlying platform
     String endOfLine =  System.getProperty("line.separator");
        
        try {
         
            out.write (endOfLine);
        
        } catch (IOException ioe) {
            throw new SAXException ("ERROR: I/O Exception thrown while printing a new line", ioe);
        }
    }

}
