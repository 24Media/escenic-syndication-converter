package gr.twentyfourmedia.syndication.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import gr.twentyfourmedia.syndication.model.Escenic;
import gr.twentyfourmedia.syndication.model.Section;
import gr.twentyfourmedia.syndication.service.SectionService;

import org.eclipse.persistence.oxm.CharacterEscapeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/section")
public class SectionController {
	
	@Autowired
	private SectionService sectionService;	
	
	@RequestMapping(value = "marshall")
	public String marshall(Model model) {

		Escenic sections = new Escenic();
		sections.setVersion("2.0");
		List<Section> allSections = sectionService.getSections();
		sections.setSectionList(sectionsFiltering(allSections));

		String path = System.getProperty("filepath.syndicationFiles") + "/write/Section_Export.xml";
		
		FileOutputStream outputStream;
		
		try {
			
			outputStream = new FileOutputStream(new File(path));
			StreamResult result = new StreamResult(outputStream);
			JAXBContext jaxbContext = JAXBContext.newInstance(Escenic.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            marshaller.setProperty("com.sun.xml.internal.bind.characterEscapeHandler",
                new CharacterEscapeHandler() {
                    @Override
                    public void escape(char[] ch, int start, int length, boolean isAttVal, Writer writer) throws IOException {
                    	writer.write(ch, start, length);
                    }
                }
            );
            
            marshaller.marshal(sections, result);
		} 
		catch(FileNotFoundException exception) {
			
			exception.printStackTrace();
		}
		catch (JAXBException exception) {

			exception.printStackTrace();
		}
		
		return "/home";
	}
	
	@RequestMapping(value = "unmarshall")
	public String unmarshall(Model model) {
		
		String path = System.getProperty("filepath.syndicationFiles") + "/read/Section_Import.xml";
		
		FileInputStream inputStream;
		
		try {
			
			inputStream = new FileInputStream(new File(path));
			StreamSource source = new StreamSource(inputStream);
			
			Escenic escenic = null;
			JAXBContext	jaxbContext = JAXBContext.newInstance(Escenic.class);
	        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        escenic = (Escenic) unmarshaller.unmarshal(source);	
			
			for(Section e : escenic.getSectionList()) {

				sectionService.persistSection(e);
			}
		}
		catch (FileNotFoundException exception) {
			
			exception.printStackTrace();
		}
		catch (JAXBException exception) {
			
			exception.printStackTrace();
		}
		
		return "/home";
	}
	
	private List<Section> sectionsFiltering(List<Section> sections) {
		
		List<Section> result = new ArrayList<Section>();
	
		for(Section s : sections) {
			
			if(s.getMirrorSourceElement() == null) { //No Mirrored Sections
				
				if(s.getParent() != null) { //Not Needed Parent Values
					s.getParent().setSource(null);
					s.getParent().setSourceId(null);
					s.getParent().setExportedDbId(null);
				}
				
				//Not Needed Section Values
				s.setSource(null);
				s.setSourceId(null);
				s.setExportedDbId(null);
				
				result.add(s);
			}
		}
		
		return result;
	}
}
