package gr.twentyfourmedia.syndication.utilities;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FieldAdapter extends XmlAdapter<String, String>{

	@Override
	public String unmarshal(String value) throws Exception {
		
		if(value.equals("")) return null; else return value; //Persist Empty Fields as NULL Values
	}

	@Override
	public String marshal(String value) throws Exception {
		
		/*
		 * Order Of Replacements Does Matter
		 */
		return value.replaceAll("<!\\[CDATA\\[", "")
					.replaceAll("\\]\\]>", "")
					.replaceAll("&", "&#38;")
					.replaceAll("&#38;lt;", "<")
					.replaceAll("&#38;gt;", ">")
					.replaceAll("&#38;quot;", "\"")
					.replaceAll("&#38;#39;", "'")
					.replaceAll("&#38;mdash;", "—")
					.replaceAll("&#38;amp;", "&#38;")
					.replaceAll(" allowfullscreen>", " allowfullscreen=\"allowfullscreen\">")
					.replaceAll(" async ", " async=\"async\" ");
	}
}
