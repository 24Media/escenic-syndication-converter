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
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private Jaxb2Marshaller marshaller;
	
	@Autowired
	private ContentService contentService;	
	
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
	
	
	
	
	
	
	@RequestMapping(value = "marshall")
	public String marshall(Model model) {

		Escenic contents = new Escenic();
		contents.setVersion("2.0");
		List<Content> allTags = contentService.getContents();
		contents.setContentList(tagsFiltering(allTags));

		String path = System.getProperty("filepath.syndicationFiles") + "/write/exported-content.xml";
		
		FileOutputStream outputStream;
		
		try {
			
			outputStream = new FileOutputStream(new File(path));
			
			StreamResult result = new StreamResult(outputStream);
			
			marshaller.marshal(contents, result);	
		} 
		catch(FileNotFoundException exception) {
			
			exception.printStackTrace();
		}
		
		return "/home";
	}	
	
	@RequestMapping(value = "unmarshall")
	public String unmarshall(Model model) {

		String path = System.getProperty("filepath.syndicationFiles") + "/read/content-tree.xml";
		
		FileInputStream inputStream;
		
		try {
			inputStream = new FileInputStream(new File(path));
			StreamSource source = new StreamSource(inputStream);
			
			Escenic escenic = (Escenic) marshaller.unmarshal(source);
			
			for(Content c : escenic.getContentList()) {

				contentService.persistContent(c);
				
				contentHTMLFields(c, path);
				
				contentService.mergeContent(c);
				
				/*
				if(c.getFieldList()!=null){
				for(Field f : c.getFieldList()) {
					
					if(f.getField()==null) { //HTML Content
					
						System.out.println("@@" + getFieldHTMLContent(path, f.getName(), c.getSourceId(), null));
					}
				}
				}
				
				if(c.getRelationSet()!=null) {
				for(Relation r : c.getRelationSet()) {
					
					if(r.getFieldList()!=null) {
					for(Field f : r.getFieldList()) {
					
						if(f.getField()==null) {
						
							System.out.println("##" + getFieldHTMLContent(path, f.getName(), c.getSourceId(), r.getSourceId()));
						}
					}
					}
				}
				}
				*/
				
				
				
				
			}
		}
		catch (FileNotFoundException exception) {
			
			exception.printStackTrace();
		} 
		
		return "/home";
	}
	
	
	
	
	private void contentHTMLFields(Content content, String path) {
		
		/*
		 * Set HTML Content Fields 
		 */
		if(content.getFieldList()!=null) {
		
			for(Field f : content.getFieldList()) {
			
				if(f.getField()==null) { //Possible HTML Content
			
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
		
						if(f.getField()==null) {
					
							String result = getFieldHTMLContent(path, f.getName(), content.getSourceId(), r.getSourceId());
							if(result!=null) f.setField(result);
						}
					}
				}
			}
		}
	}
	
	
	
	
	
	private List<Content> tagsFiltering(List<Content> contents) {
		
		List<Content> result = new ArrayList<Content>();
		
		for(Content c : contents) {
							
			//Not Needed Tag Values
			c.setCreator(null);
			c.setAuthorSet(null);
			
			result.add(c);
		}
		
		return result;
	}
}