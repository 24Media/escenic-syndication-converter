package gr.twentyfourmedia.syndication.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import gr.twentyfourmedia.syndication.model.Escenic;
import gr.twentyfourmedia.syndication.model.Section;
import gr.twentyfourmedia.syndication.service.SectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/section")
public class SectionController {

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private Jaxb2Marshaller marshaller;
	
	@Autowired
	private SectionService sectionService;	
	
	@RequestMapping(value = "list")
	public String list(Model model) {

		model.addAttribute("sectionList", sectionService.getSections());
		return "section/list";
	}
	
	@RequestMapping(value = "marshall")
	public String marshall(Model model) {

		Escenic sections = new Escenic();
		sections.setVersion("2.0");
		List<Section> allSections = sectionService.getSections();
		
		/*
		 * Include Only Non Mirrored Sections
		 */
		List<Section> selectedSections = new ArrayList<Section>();
		
		for(Section s : allSections) {
			
			if(s.getMirrorSourceElement() == null) {
				
				//Remove Not Needed Parent Values
				if(s.getParent() != null) {
					s.getParent().setSource(null);
					s.getParent().setSourceId(null);
					s.getParent().setExportedDbId(null);
				}
				
				//Remove Not Needed Section Values
				s.setSource(null);
				s.setSourceId(null);
				s.setExportedDbId(null);
				
				selectedSections.add(s);
			}
		}
		sections.setSectionList(selectedSections);
		
		
		
		
		
		String path = System.getProperty("filepath.syndicationFiles") + "/write/exportedsections.xml";
		
		FileOutputStream outputStream;
		
		try {
			
			outputStream = new FileOutputStream(new File(path));
			
			StreamResult result = new StreamResult(outputStream);
			
			marshaller.marshal(sections, result);	
		} 
		catch(FileNotFoundException exception) {
			
			exception.printStackTrace();
		}
		
		return "section/list";
	}
	
	@RequestMapping(value = "unmarshall")
	public String unmarshall(Model model) {

		String path = System.getProperty("filepath.syndicationFiles") + "/read/sectionstree.xml";
		
		FileInputStream inputStream;
		
		try {
			inputStream = new FileInputStream(new File(path));
			StreamSource source = new StreamSource(inputStream);
			
			Escenic escenic = (Escenic) marshaller.unmarshal(source);
			
			for(Section e : escenic.getSectionList()) {

				sectionService.persistSection(e);
			}
		}
		catch (FileNotFoundException exception) {
			
			exception.printStackTrace();
		} 
		
		return "section/list";
	}	
}
