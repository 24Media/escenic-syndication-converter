package gr.twentyfourmedia.syndication.utilities;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FieldAdapter extends XmlAdapter<String, String>{

	@Override
	public String unmarshal(String value) throws Exception {
		
		if(value.equals("")) return null; else return value; //Persist Empty Fields as NULL Values
	}

	@Override
	public String marshal(String value) throws Exception {


		return value.replaceAll("<!\\[CDATA\\[", "")
					.replaceAll("\\]\\]>", "");
		
		/*
					.replace("&lt;", "&#60;")
					.replace("&gt;", "&#62;")
					.replace("&amp;", "&#38;")
					.replace("&quot", "&#34;");
		*/
		//return value;
		//return filterMarshalingValue(value);
	}
}
