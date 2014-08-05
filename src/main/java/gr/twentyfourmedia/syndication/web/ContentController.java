package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Escenic;
import gr.twentyfourmedia.syndication.model.RelationInlineProblem;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.FieldService;
import gr.twentyfourmedia.syndication.utilities.CustomException;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;	
	
	@Autowired
	private FieldService fieldService;
	
	/**
	 * Marshall Contents Read From Database, Given an Id or Content's Type
	 * @param id Application Id Of A Specific Content
	 * @param type Content Type
	 * @return ModelAndView Object
	 */
	@RequestMapping(value = "marshall")
	public ModelAndView marshallToOneFile(@RequestParam(value = "random", required = false) String random,
										  @RequestParam(value = "type", required = true) String type,
										  @RequestParam(value = "problemId", required = false) Integer problemId)
										  throws Exception {

		Escenic contents = new Escenic();
		contents.setVersion("2.0");
		List<Content> contentsList = new ArrayList<Content>();
		
		String path = System.getProperty("filepath.syndicationFiles") + "/write/";
		String filename = null;
		
		int cannotCorrectDuplicates = 0;
		
		if(random != null) { //A Random Content For Demonstration
		
			filename = path + "Random_Content";
			contentsList.add(contentService.getRandomContent("excludeEverything"));
			contents.setContentList(filterOutElementsAndAttributes(contentsList));		
		}
		else if(type.equals("tag") || type.equals("picture") || type.equals("multipleTypeVideo")) { //Export Everything Without Examining 'problemId'

			if(type.equals("tag")) filename = path + "Tag.xml";
				else if(type.equals("picture")) filename = path + "Picture.xml"; 
					else filename = path + "MultipleTypeVideo.xml";
			
			contents.setContentList(filterOutElementsAndAttributes(contentService.getContentsByType(type, "excludeEverything")));
		}
		else if(type.equals("photostory")) {
		
			//TODO Handle Case
		}
		else if(type.equals("news")) {
			
			/*
			 * 	0	:	null - null
			 * 	1	:	MISSING_RELATIONS - null
			 * 	2	:	null - RELATIONS_CAN_BE_REPLACED
			 * 	3	:	MISSING_RELATIONS - RELATIONS_CAN_BE_REPLACED
			 */
			if(problemId == 0) {
				
				filename = path + "News.xml";
				contents.setContentList(filterOutElementsAndAttributes(contentService.getContentsByTypeContentProblemRelationInlineProblem("news", null, null, "excludeEverything")));
			}
			else if(problemId == 1) {
				
				filename = path + "News_#PID1.xml";
				//TODO Handle
			}
			else if(problemId == 2) {
						
				filename = path + "News_#PID2.xml";
				
				List<Content> temporaryList = new ArrayList<Content>();

				for(Content c : contentService.getContentsByTypeContentProblemRelationInlineProblem("news", null, RelationInlineProblem.RELATIONS_CAN_BE_REPLACED, "excludeAuthors")) {
			
					Content temporaryContent = contentService.replaceDuplicateRelationsInlineWithAnchors(c);
					
					if(temporaryContent != null) {
						
						temporaryList.add(temporaryContent);
					}
					else {
						
						cannotCorrectDuplicates++; //Will Be Added To ModelAndView
					}
				}
				
				contents.setContentList(filterOutElementsAndAttributes(temporaryList));				
			}
			else if(problemId == 3) {
				
				filename = path + "News_#PID3.xml";
				//TODO Handle
			}
			else { //Other 'problemId' Given
				
				throw new CustomException("Marshalling Controller Can Not Handle The Given Problem Type");
			}
		}
		else { //Other 'type' Given
			
			throw new CustomException("Marshalling Controller Can Not Handle The Given Content Type");
		}
		
		/*
		 * Marshall Contents
		 */
		FileOutputStream outputStream;
		
		try {
			
			outputStream = new FileOutputStream(new File(filename));
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
			
			throw new CustomException("Make Sure That ${syndicationFiles}/write/ Folder Exists");
		}

		/*
		 * Add To Model Some Information About What Happened
		 */
		ModelAndView model = new ModelAndView("administrator/results");
		//Input Values
		model.addObject("random", random);
		model.addObject("type", type);
		model.addObject("problemId", problemId);
		//Result Values
		model.addObject("countMarshalled", contents.getContentList().size());
		model.addObject("cannotCorrectDuplicates", cannotCorrectDuplicates);
		
		return model;
	}
	
	/**
	 * Marshall Contents Read From Database Given Content's Type
	 * @param type Content Type
	 * @param itemsPerFile Items Per File
	 * @return ModelAndView Object
	 */
	@RequestMapping(value = "marshallToMultipleFiles")
	public ModelAndView marshallToMultipleFiles(@RequestParam(value = "type", required = true) String type,
										  	    @RequestParam(value = "itemsPerFile") int itemsPerFile) {

		List<Content> contentsList = new ArrayList<Content>();
		contentsList.addAll(contentService.getContentsByType(type, "excludeEverything"));
		
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

		ModelAndView model = new ModelAndView("redirect:/");
		return model;
	}	

	@RequestMapping(value = "unmarshall")
	public ModelAndView unmarshall() throws Exception {
		
		String path = System.getProperty("filepath.syndicationFiles") + "/read/Contents_Import.xml";
		
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
			
			throw new CustomException("${syndicationFiles}/read/Contents_Import.xml File Does Not Exist");
		}
		
		ModelAndView model = new ModelAndView("redirect:/");
		return model;
	}
	
	//TODO Create View For Delete Action
	@RequestMapping(value = "delete")
	public ModelAndView delete(	@RequestParam(value = "applicationId", required = false) Long applicationId,
								@RequestParam(value = "sourceId", required = false) String sourceId) {
		
		Content content = null;
		
		if(applicationId != null) content = contentService.getContent(applicationId, null);
			else if(sourceId != null) content = contentService.getContent(sourceId, null);
				else throw new CustomException("Define Content's applicationId or sourceId.");
		
		if(content != null) {
			
			contentService.deleteContent(content.getApplicationId());
		}
		else {
		
			throw new CustomException("Content Does Not Exist.");
		}
		
		ModelAndView model = new ModelAndView("redirect:/");
		return model;
	}
	
	
	//TODO A Filter For Creator, or something
	//TODO Check That No Authors or Creators Gets Exported
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
}