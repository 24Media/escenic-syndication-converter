package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Escenic;
import gr.twentyfourmedia.syndication.service.ContentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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