package gr.twentyfourmedia.syndication.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtilities {

	/**
	 * Read Marshalled Syndication File And Unescape HTML Characters like '&lt;', '&gt;' Than Can Not Imported Properly To Escenic
	 * @param path Path To Marshalled Syndication File
	 */
	public static void replaceHTMLTokens(String path) {
		
		File file = new File(path);
		String fileContents;
		
		try {
			
			fileContents = FileUtils.readFileToString(new File(path));

			/*
			 * '>' Character (For Example) Will Be Escaped to '&gt;'. But If Your Text Actually Contains '&gt;' It Will 
			 * Be Escaped To '&amp;gt;'. So If You Can Not Find A Way To Completely Disable Character Escaping and You 
			 * Need To Do Some String Replacing, Special Consideration Is Needed To Remove This Kind Of Garbage 
			 */
			fileContents = fileContents
							.replaceAll("&amp;lt;", "<")
							.replaceAll("&amp;gt;", ">")
							.replaceAll("&amp;quot;", "\"")
							.replaceAll("&amp;#39;", "'")
							.replaceAll("&amp;mdash;", "-")
							.replaceAll("&amp;amp;", "&#38;")
							.replaceAll("&lt;", "<")
							.replaceAll("&gt;", ">")
							.replaceAll("&quot;", "\"")
							.replaceAll("&#39;", "'")
							.replaceAll("&mdash;", "-")
							.replaceAll("&amp;", "&#38;");

			FileUtils.writeStringToFile(file, fileContents);		
		} 
		catch (IOException exception) {
			
			exception.printStackTrace();
		}
	}
}
