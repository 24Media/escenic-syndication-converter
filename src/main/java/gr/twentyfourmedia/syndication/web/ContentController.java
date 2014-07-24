package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Escenic;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.FieldService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.oxm.CharacterEscapeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;	
	
	@Autowired
	private FieldService fieldService;
	
	public static final List<String> KAIROS_SECTIONS;
	static {
		List<String> temporary = new ArrayList<String>();
		temporary.add("kairos");
		temporary.add("kairos-eidiseis");
		temporary.add("kairos-environment");
		temporary.add("kairos-lifestyle");
		temporary.add("kairos-taksidi");
		KAIROS_SECTIONS = temporary;
	}
	
	/**
	 * Marshall Contents Read From Database, Given an Id, Content's Type et al.
	 * @param id Application Id Of A Specific Content
	 * @param type Content Type
	 * @param model Model
	 * @return View To Be Rendered
	 */
	@RequestMapping(value = "marshall")
	public String marshallToOneFile(@RequestParam(value = "id", required = false) Long id,
									@RequestParam(value = "type", required = false) String type, 
									@RequestParam(value = "homeSections", required = false) String homeSections,
									@RequestParam(value = "homeSectionsExcluded", required = false) String homeSectionsExcluded,
									Model model) {

		Escenic contents = new Escenic();
		contents.setVersion("2.0");
		List<Content> contentsList = new ArrayList<Content>();
		
		if(id != null) { //A Single Content Item
		
			contentsList.add(contentService.getFilteredContent(id));
			contents.setContentList(filterOutElementsAndAttributes(contentsList));
		}
		else if(type != null && homeSections != null) { //Content Items Of Specified Home Sections

			if(homeSectionsExcluded != null) { //Excluded

				//TODO Filter Content Items
				contents.setContentList(filterOutElementsAndAttributes(contentService.getContentsByTypeExcludingHomeSections(type, homeSectionsListFromString(homeSections))));
			}
			else { //Included
			
				//TODO Filter Content Items
				contents.setContentList(filterOutElementsAndAttributes(contentService.getContentsByTypeAndHomeSections(type, homeSectionsListFromString(homeSections))));
			}
		}
		else if(type != null) { //Content Items Of Specified Type
			
			contents.setContentList(filterOutElementsAndAttributes(contentService.getFilteredContentsByType(type)));
		}
		else { //All Content Items
			
			contents.setContentList(filterOutElementsAndAttributes(contentService.getFilteredContents()));
		}
		
		String path = System.getProperty("filepath.syndicationFiles") + "/write/Contents_Export.xml";
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
            
            marshaller.marshal(contents, result);
		} 
		catch(FileNotFoundException exception) {
			
			exception.printStackTrace();
		} 
		catch (JAXBException exception) {

			exception.printStackTrace();
		}

		return "/home";
	}
	
	/**
	 * Marshall Contents Read From Database, Given an Id, Content's Type et al.
	 * @param type Content Type
	 * @param itemsPerFile Items Per File
	 * @param model Model
	 * @return View To Be Rendered
	 */
	@RequestMapping(value = "marshallToMultipleFiles")
	public String marshallToMultipleFiles(@RequestParam(value = "type", required = false) String type,
										  @RequestParam(value = "homeSections", required = false) String homeSections,
										  @RequestParam(value = "homeSectionsExcluded", required = false) String homeSectionsExcluded,
										  @RequestParam(value = "itemsPerFile") int itemsPerFile,
										  Model model) {

		List<Content> contentsList = new ArrayList<Content>();
		
		if(type != null && homeSections != null) { //Content Items Of Specified Home Sections

			if(homeSectionsExcluded != null) { //Excluded

				//TODO Filter Content Items
				contentsList.addAll(contentService.getContentsByTypeExcludingHomeSections(type, homeSectionsListFromString(homeSections)));
			}
			else { //Included
			
				//TODO Filter Content Items
				contentsList.addAll(contentService.getContentsByTypeAndHomeSections(type, homeSectionsListFromString(homeSections)));
			}
		}
		else if(type != null) {
			
			contentsList.addAll(contentService.getFilteredContentsByType(type));
		}
		else {
			
			contentsList.addAll(contentService.getFilteredContents());
		}

		Escenic escenic = new Escenic();
		escenic.setVersion("2.0");
		
		int contentCounter = 0;
		int fileCounter = 0;
		
		Iterator<Content> iterator = contentsList.iterator();
		List<Content> escenicContents = new ArrayList<Content>();
		
		while(iterator.hasNext()) {
		
			Content current = iterator.next();
			escenicContents.add(current);
			contentCounter++;
			
			if(contentCounter % itemsPerFile == 0 || contentCounter == contentsList.size()) {
				
				escenic.setContentList(filterOutElementsAndAttributes(escenicContents));
				
				String fileName = itemsPerFile == 1 ? "Id_" + current.getApplicationId() : "File_" + fileCounter++;
				String path = System.getProperty("filepath.syndicationFiles") + "/write/Contents_Export_" + fileName + ".xml";
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
		            
		            marshaller.marshal(escenic, result);
				} 
				catch(FileNotFoundException exception) {
					
					exception.printStackTrace();
				}
				catch (JAXBException exception) {

					exception.printStackTrace();
				}
				
				//Before Continuing Create A New Escenic Object
				escenic = new Escenic();
				escenic.setVersion("2.0");
				escenicContents = new ArrayList<Content>();
			}
		}

		return "/home";
	}	

	@RequestMapping(value = "unmarshall")
	public String unmarshall(Model model) {
		
		String path = System.getProperty("filepath.syndicationFiles") + "/read/Contents_Input.xml";
		
		FileInputStream inputStream;
		
		try {
			inputStream = new FileInputStream(new File(path));
			StreamSource source = new StreamSource(inputStream);
			
			Escenic escenic = null;
			JAXBContext	jaxbContext = JAXBContext.newInstance(Escenic.class);
	        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        escenic = (Escenic) unmarshaller.unmarshal(source);
			
			for(Content c : escenic.getContentList()) {

				if(!contentService.contentExists(c.getSourceId())) { //Persist Contents That Do Not Already Exist
					
					contentService.persistContent(c);
					contentService.handleContentHTMLFields(c, path);	
					contentService.mergeContent(c, true);		
				}
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
	
	/**
	 * Given a List of Contents Filter Out Elements or Attributes That Are Not Needed In The Exported File 
	 * @param contents List Of Input Contents
	 * @return List Of Output Contents
	 */
	private List<Content> filterOutElementsAndAttributes(List<Content> contents) {
		
		List<Content> result = new ArrayList<Content>();
		
		for(Content c : contents) {
							
			c.setCreator(null);
			//c.setAuthorSet(null);
			
			result.add(c);
		}
		
		return result;
	}
	
	/**
	 * Given a General Description For Home Sections Build A List of All Actual Home Sections Related To That Description 
	 * @param homeSections General Description
	 * @return List of Home Sections
	 */
	private List<String> homeSectionsListFromString(String homeSections) {
		
		List<String> result = new ArrayList<String>();
		
		if(homeSections.equals("kairos")) {
			
			result = KAIROS_SECTIONS;
		}
		
		return result;
	}
}