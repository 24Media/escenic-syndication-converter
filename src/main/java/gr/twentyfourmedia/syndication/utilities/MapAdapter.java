package gr.twentyfourmedia.syndication.utilities;

import java.io.*;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.DomHandler;
import javax.xml.transform.Source;
import javax.xml.transform.stream.*;

public class MapAdapter implements DomHandler<String, StreamResult> {

    private static final String BIO_START_TAG = "<bio>";
    private static final String BIO_END_TAG = "</bio>";

    private StringWriter xmlWriter = new StringWriter();

    public StreamResult createUnmarshaller(ValidationEventHandler errorHandler) {
    	
    	xmlWriter.getBuffer().setLength(0);
    	return new StreamResult(xmlWriter);
    }
    
    
    public String getElement(StreamResult rt) {
        String xml = rt.getWriter().toString();
        //int beginIndex = xml.indexOf(BIO_START_TAG) + BIO_START_TAG.length();
        //int endIndex = xml.indexOf(BIO_END_TAG);
        //return xml.substring(beginIndex, endIndex);
    
    	return "<![CDATA[" + xml + "]]>";
    	
    }

    public Source marshal(String n, ValidationEventHandler errorHandler) {
        try {
            String xml = BIO_START_TAG + n.trim() + BIO_END_TAG;
            StringReader xmlReader = new StringReader(xml);
            return new StreamSource(xmlReader);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
