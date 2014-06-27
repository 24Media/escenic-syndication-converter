package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Escenic;
import gr.twentyfourmedia.syndication.model.Field;
import gr.twentyfourmedia.syndication.model.Relation;
import gr.twentyfourmedia.syndication.service.ContentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private Jaxb2Marshaller marshaller;
	
	@Autowired
	private ContentService contentService;	
	
	/**
	 * Given a Content or A Content's Relation, Parse Syndication File and Wrap Found Value With <![CDATA[...]]> Tokens
	 * @param path Path To Syndication File
	 * @param htmlField Field HTML Name
	 * @param contentSourceId Content's Source Id
	 * @param relationSourceId Relation's Source Id
	 * @return Content or Relation's Value Wrapped With <![CDATA[...]]> Tokens
	 */
	@SuppressWarnings("unchecked")
	private String getFieldHTMLContent(String path, String htmlField, String contentSourceId, String relationSourceId) {
        
		Document doc;
        String result = null;
        
        try {
        	
            File xml = new File(path);
            SAXReader reader = new SAXReader();
            doc = reader.read(xml);
            Element root = doc.getRootElement();
            List<Element> contents = root.elements("content");
            List<Element> fields;
            
            for(Element content : contents) {
            	
            	String contentId = content.attributeValue("sourceid");
            	
                if(contentId!=null && !contentId.isEmpty() && contentId.equals(contentSourceId)) {
                	
                	if(relationSourceId == null) { //Content Fields
                		
	                	fields = content.elements("field");
	                    
	                    for(Element field : fields) {
	                        
	                    	String fieldName = field.attributeValue("name");
	                        
	                        if(fieldName.equals(htmlField)) {
	                        	
	                        	result = field.asXML();
	                            result = result.replaceAll("<field xmlns=\"http://xmlns.escenic.com/2009/import\" name=\"" + htmlField + "\">", "");
	                            result = result.replaceAll("<field xmlns=\"http://xmlns.escenic.com/2009/import\" name=\"" + htmlField + "\"/>", "");
	                            result = result.replaceAll("</field>", "");
	                            result = "<![CDATA[" + result + "]]>";
	                        }
	                    }
                	}
                	else { //Content Relation Fields
                		
                    	List<Element> relations = content.elements("relation");
                    	
                    	for(Element relation : relations) {
                    		
                    		String relationId = relation.attributeValue("sourceid");
                    		
                    		if(relationId!=null && !relationId.isEmpty() && relationId.equals(relationSourceId)) {

    		                	fields = relation.elements("field");
    		                    
    		                    for(Element field : fields) {
    		                        
    		                    	String fieldName = field.attributeValue("name");
    		                        
    		                        if(fieldName.equals(htmlField)) {
    		                        	
    		                        	result = field.asXML();
    		                            result = result.replaceAll("<field xmlns=\"http://xmlns.escenic.com/2009/import\" name=\"" + htmlField + "\">", "");
    		                            result = result.replaceAll("<field xmlns=\"http://xmlns.escenic.com/2009/import\" name=\"" + htmlField + "\"/>", "");
    		                            result = result.replaceAll("</field>", "");
    		                            result = "<![CDATA[" + result + "]]>";
    		                        }
    		                    }
                    		}
                    	}
                	}
                }
            }
        }
        catch(DocumentException exception) {
        
        	System.out.println(exception);
        }
        
        if(result!=null && !result.equals("<![CDATA[]]>")) return result; else return null;
    }	

	@RequestMapping(value = "marshallToOneFile")
	public String marshallToOneFile(@RequestParam(value = "id", required = false) Long id, 
									@RequestParam(value = "type", required = false) String type, 
									Model model) {

		Escenic contents = new Escenic();
		contents.setVersion("2.0");
		List<Content> contentsList = new ArrayList<Content>();
		 
		if(id != null) { //A Single Content Item
		
			contentsList.add(contentService.getContent(id));
			contents.setContentList(filterOutElementsAndAttributes(contentsList));
		}
		else if(type != null) { //Content Items Of Specified Type
			
			contents.setContentList(filterOutElementsAndAttributes(contentService.getContentsByType(type)));
		}
		else { //All Content Items
			
			contents.setContentList(filterOutElementsAndAttributes(contentService.getContents()));
		}
		

		String path = System.getProperty("filepath.syndicationFiles") + "/write/content_export.xml";
		FileOutputStream outputStream;
		
		try {
			
			outputStream = new FileOutputStream(new File(path));
			StreamResult result = new StreamResult(outputStream);
			marshaller.marshal(contents, result);
			replaceHTMLTokens(path);
		} 
		catch(FileNotFoundException exception) {
			
			exception.printStackTrace();
		}

		return "/home";
	}
	
	//TODO Number Of Contents Per File As A Parameter
	@RequestMapping(value = "marshallToMultipleFiles")
	public String marshallToMultipleFiles(@RequestParam(value = "id", required = false) Long id, 
										  @RequestParam(value = "type", required = false) String type, 
										  Model model) {

		List<Content> contentsList = new ArrayList<Content>();
		 
		if(id != null) {
		
			contentsList.add(contentService.getContent(id));
		}
		else if(type != null) {
			
			contentsList.addAll(contentService.getContentsByType(type));
		}
		else {
			
			contentsList.addAll(contentService.getContents());
		}
		
		for(Content c : contentsList) {
			
			Escenic contents = new Escenic();
			contents.setVersion("2.0");
			List<Content> aContent = new ArrayList<Content>();
			aContent.add(c);
			contents.setContentList(filterOutElementsAndAttributes(aContent));
			
			String path = System.getProperty("filepath.syndicationFiles") + "/write/content_export_" + c.getApplicationId() + ".xml";
			FileOutputStream outputStream;
			
			try {
				
				outputStream = new FileOutputStream(new File(path));
				StreamResult result = new StreamResult(outputStream);
				marshaller.marshal(contents, result);
				replaceHTMLTokens(path);
			} 
			catch(FileNotFoundException exception) {
				
				exception.printStackTrace();
			}	
		}
		
		return "/home";
	}	
	
	@RequestMapping(value = "unmarshall")
	public String unmarshall(Model model) {

		String path = System.getProperty("filepath.syndicationFiles") + "/read/content-tree.xml";
		/*
		FileInputStream inputStream;
		
		try {
			inputStream = new FileInputStream(new File(path));
			StreamSource source = new StreamSource(inputStream);
			
			Escenic escenic = (Escenic) marshaller.unmarshal(source);
			
			for(Content c : escenic.getContentList()) {

				contentService.persistContent(c);
				contentHTMLFields(c, path);	
				contentService.mergeContent(c);		
			}
		}
		catch (FileNotFoundException exception) {
			
			exception.printStackTrace();
		} 
		*/
		return "/home";
	}

	/**
	 * If <![CDATA[...]]> Element Created, Set Corresponding Object Attributes To Ensure That Merging Of Content Will Merge These Attributes Too 
	 * @param content Content Object
	 * @param path Path To Syndication File
	 */
	private void contentHTMLFields(Content content, String path) {
		
		/*
		 * Set HTML Content Fields 
		 */
		if(content.getFieldList()!=null) {
		
			for(Field f : content.getFieldList()) {
			
				if(f.getField()==null) { //Possible HTML Value
			
					String result = getFieldHTMLContent(path, f.getName(), content.getSourceId(), null);
					if(result!=null) f.setField(result);
				}
			}
		}
		
		/*
		 * Set HTML Content Relation Field
		 */
		if(content.getRelationSet()!=null) {
		
			for(Relation r : content.getRelationSet()) {
		
				if(r.getFieldList()!=null) {
		
					for(Field f : r.getFieldList()) {
		
						if(f.getField()==null) { //Possible HTML Value
					
							String result = getFieldHTMLContent(path, f.getName(), content.getSourceId(), r.getSourceId());
							if(result!=null) f.setField(result);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Read Marshalled Syndication File And Unescape HTML Characters like '&lt;', '&gt;' Than Can Not Imported Properly To Escenic
	 * @param path Path To Marshalled Syndication File
	 */
	private void replaceHTMLTokens(String path) {
		
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
							.replaceAll("&amp;amp;", "&")
							.replaceAll("&lt;", "<")
							.replaceAll("&gt;", ">")
							.replaceAll("&quot;", "\"")
							.replaceAll("&amp;", "&");

			FileUtils.writeStringToFile(file, fileContents);			
		} 
		catch (IOException exception) {
			
			exception.printStackTrace();
		}
	}
	
	/**
	 * Given a List of Contents Filter Out Elements or Attributes That Are Not Needed In The Exported File 
	 * @param contents List Of Input Contents
	 * @return List Of Output Contents
	 */
	private List<Content> filterOutElementsAndAttributes(List<Content> contents) {
		
		List<Content> result = new ArrayList<Content>();
		
		for(Content c : contents) {
							
			//Not Needed Attributes
			c.setExportedDbId(null);
			
			//Not Needed Elements
			c.setCreator(null);
			c.setAuthorSet(null);
			
			result.add(c);
		}
		
		return result;
	}
}