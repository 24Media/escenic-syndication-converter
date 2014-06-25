package gr.twentyfourmedia.syndication.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;

public class MapAdapter extends XmlAdapter<MapWrapper, Map<String,String>> {
    
	@Override
    public MapWrapper marshal(Map<String,String> m) throws Exception {
		/*
    MapWrapper wrapper = new MapWrapper();
    List<JAXBElement<String>> elements = new ArrayList<JAXBElement<String>>();
       for (Map.Entry<String, String> property: m.entrySet()) {
          elements.add(new JAXBElement<String>(
                    new QName(getCleanLabel(property.getKey())), 
          String.class, property.getValue()));
       }
       wrapper.elements=elements;
    return wrapper;
    */
		return new MapWrapper();
	}

	@Override
	public Map<String,String> unmarshal(MapWrapper map) throws Exception {
	         
	

	    
		
	    
	    for(JAXBElement<String> v : map) {
	    	
	    }
	    
	    
	}
	
	// Return a lower-camel XML-safe attribute
	private String getCleanLabel(String attributeLabel) { 
		/*
	    attributeLabel = attributeLabel.replaceAll("[()]", "")
	            .replaceAll("[^\\w\\s]", "_").replaceAll(" ", "_")
	            .toUpperCase();
	    attributeLabel = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
	            attributeLabel);
	    return attributeLabel;
	    */
	}
}
