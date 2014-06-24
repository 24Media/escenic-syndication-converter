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
	
	@RequestMapping(value = "list")
	public String list(Model model) {

		model.addAttribute("contentList", contentService.getContents());
		return "section/list";
	}	
	
	@RequestMapping(value = "marshall")
	public String marshall(Model model) {

		Escenic contents = new Escenic();
		contents.setVersion("2.0");
		List<Content> allTags = contentService.getContents();
		
		/*
		 * Include Only Non Mirrored Sections
		 */
		List<Content> selectedTags = new ArrayList<Content>();
		
		for(Content c : allTags) {
							
			//Remove Not Needed Tag Values
			c.setCreator(null);
			c.setAuthorList(null);
			
			selectedTags.add(c);
		}
		contents.setContentList(selectedTags);
		
		
		
		
		
		String path = System.getProperty("filepath.syndicationFiles") + "/write/exportedTags.xml";
		
		FileOutputStream outputStream;
		
		try {
			
			outputStream = new FileOutputStream(new File(path));
			
			StreamResult result = new StreamResult(outputStream);
			
			marshaller.marshal(contents, result);	
		} 
		catch(FileNotFoundException exception) {
			
			exception.printStackTrace();
		}
		
		return "section/list";
	}	
	
	@RequestMapping(value = "unmarshall")
	public String unmarshall(Model model) {

		String path = System.getProperty("filepath.syndicationFiles") + "/read/tagstree.xml";
		
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
		
		return "section/list";
	}	
}