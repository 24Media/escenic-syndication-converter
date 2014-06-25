package gr.twentyfourmedia.syndication.utilities;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.DomHandler;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class FieldDomHandler implements DomHandler<String, StreamResult> {

	private StringWriter xmlWriter = new StringWriter();
	
	@Override
	public StreamResult createUnmarshaller(ValidationEventHandler errorHandler) {
		
		return new StreamResult(xmlWriter);
	}

	@Override
	public String getElement(StreamResult rt) {
		
		String xml = rt.getWriter().toString();
        //int beginIndex = xml.indexOf(BIO_START_TAG) + BIO_START_TAG.length();
        //int endIndex = xml.indexOf(BIO_END_TAG);
        //return xml.substring(beginIndex, endIndex);
		return xml;
	}

	@Override
	public Source marshal(String n, ValidationEventHandler errorHandler) {
		
		try {
            //String xml = BIO_START_TAG + n.trim() + BIO_END_TAG;
            //StringReader xmlReader = new StringReader(xml);
            StringReader xmlReader = new StringReader(n);
            
            return new StreamSource(xmlReader);
        }
		catch(Exception e) {
            
			throw new RuntimeException(e);
        }
	}
}