package gr.twentyfourmedia.syndication.utilities;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FieldAdapter extends XmlAdapter<String, String>{

	@Override
	public String unmarshal(String value) throws Exception {
		
		if(value.equals("")) return null; else return value;
	
	

	}

	@Override
	public String marshal(String value) throws Exception {
		
		return value;
	}
}
