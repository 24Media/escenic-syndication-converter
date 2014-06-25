package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Escenic;
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
	
	private String getStoriesHTMLContent(String path, String htmlField,String srcId) {
        Document doc;
        String id = "";
        String body = "";
        String prologue = "";
        String name = "";
        
        try {
            File xml = new File(path);
            SAXReader reader = new SAXReader();
            doc = reader.read(xml);
            Element root = doc.getRootElement();
            //String contentxpath = "/*[name()='escenic']/*[name()='content']/*[name()='field']";
            List<Element> contents = root.elements("content");
            List<Element> fields;
            for (Element content : contents) {
                id = content.attributeValue("sourceid");
                if (id!=null && !(id.isEmpty()) && id.equals(srcId)) {
                    fields = content.elements("field");
                    for (Element field : fields) {
                        name = field.attributeValue("name");
                        if (name.equals("body")) {
                            body = field.asXML();
                            body = body.replaceAll("<field xmlns=\"http://xmlns.escenic.com/2009/import\" name=\"body\">", "");
                            body = body.replaceAll("</field>", "");
                            body = "<![CDATA[" + body + "]]>";
                        }
                        if (name.equals("prologue")) {
                            prologue = field.asXML();
                            prologue = prologue.replaceAll("<field xmlns=\"http://xmlns.escenic.com/2009/import\" name=\"prologue\">", "");
                            prologue = prologue.replaceAll("</field>", "");
                            prologue = "<![CDATA[" + prologue + "]]>";
                        }
                    }
                }
            }
            
        } catch (DocumentException ex) {
            Logger.getLogger(SectionController.class.getName()).log(Level.ERROR, null, ex);
        }
        if (htmlField.equals("prologue")) {
            return prologue;
        } else {
            return body;
        }
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
			}
		}
		catch (FileNotFoundException exception) {
			
			exception.printStackTrace();
		} 
		
		return "/home";
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