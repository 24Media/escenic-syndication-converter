package gr.twentyfourmedia.syndication.utilities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;

public class MapWrapper {

	@XmlAnyElement
	public List<JAXBElement<String>> properties = new ArrayList<JAXBElement<String>>();
}