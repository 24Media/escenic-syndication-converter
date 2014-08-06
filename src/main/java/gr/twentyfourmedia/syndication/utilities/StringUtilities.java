package gr.twentyfourmedia.syndication.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtilities {

	/**
	 * Replace nth Occurrence Of A Substring Inside A String
	 * @param searchIn String To Search In
	 * @param searchFor String To Search For
	 * @param replaceWith String To Replace nth Occurrence
	 * @param occurence Integer Defining Which Occurrence To Replace
	 * @return String result
	 */
	public static String replaceNthOccurrence(String searchIn, String searchFor, String replaceWith, int occurrence) {
		
		StringBuffer buffer = new StringBuffer();
		Pattern pattern = Pattern.compile(searchFor);
		Matcher matcher = pattern.matcher(searchIn);
	
		int count = 0;
	
		while(matcher.find()) {
			
			count++;
			
			if(count == occurrence) {
				matcher.appendReplacement(buffer, replaceWith);
				break;
			}
		}
	
		matcher.appendTail(buffer);
		
		return buffer.toString();
	}
}
