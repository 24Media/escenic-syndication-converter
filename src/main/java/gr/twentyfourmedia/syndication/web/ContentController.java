package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.ContentProblem;
import gr.twentyfourmedia.syndication.model.Escenic;
import gr.twentyfourmedia.syndication.model.Relation;
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
import java.util.Set;

import javax.xml.bind.JAXBContext;
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
	 * Marshall Contents Read From Database
	 * @param random If Given A Random Content Will Be Marshalled
	 * @param type Content Type
	 * @param problem Value Specifying Combined contentProblem and relationInlineProblem For 'news' Contents
	 * @param itemsPerFile Items Per File
	 * @return ModelAndView Object
	 */
	@RequestMapping(value = "marshall")
	public ModelAndView marshall(@RequestParam(value = "random", required = false) String random,
								 @RequestParam(value = "type", required = true) String type,
								 @RequestParam(value = "problem", required = false) String problem,
								 @RequestParam(value = "itemsPerFile", required = true) int itemsPerFile) 
								 throws Exception {
		
		List<Content> contentsList = new ArrayList<Content>(); //Fill This List With Content Satisfying The Criteria Given

		String path = System.getProperty("filepath.syndicationFiles") + "/write/";
		String filenamePrefix = null;
		
		int contentCounter = 0;
		int fileCounter = 0;
		int cannotCorrectDuplicates = 0;
		
		if(random != null) { //A Random Content Without Problems
		
			filenamePrefix = path + "Random";
			contentsList.add(contentService.getRandomContent("excludeEverything"));
		}
		else if(type.equals("tag") || type.equals("picture") || type.equals("multipleTypeVideo")) { //Export Everything Without Examining Problems

			if(type.equals("tag")) filenamePrefix = path + "Tag";
				else if(type.equals("picture")) filenamePrefix = path + "Picture"; 
					else filenamePrefix = path + "MultipleTypeVideo";
		
			contentsList.addAll(contentService.getContentsByType(type, "excludeEverything"));
		}
		else if(type.equals("news") || type.equals("photostory")) {
			
			if(itemsPerFile != 1) throw new CustomException("'photostory' and 'news' Contents Can Not Be Marshalled In Batches"); //Self Explanatory
			if(type.equals("photostory") && (problem.equals("C") || problem.equals("D"))) throw new CustomException("Marshalling Controller Can Not Handle The Given Problem Type For 'photostory'"); //Don't Go Any Further
			
			/*
			 * 	A :	null - null
			 * 	B :	MISSING_RELATIONS - null
			 * 	C :	null - RELATIONS_CAN_BE_REPLACED
			 * 	D :	MISSING_RELATIONS - RELATIONS_CAN_BE_REPLACED
			 */
			if(problem.equals("A")) { //No Special Processing Needed
			
				if(type.equals("news")) filenamePrefix = path + "News_A"; else filenamePrefix = path + "Photostory";
				contentsList.addAll(contentService.getContentsByTypeContentProblemRelationInlineProblem(type, null, null, "excludeEverything"));	
			}
			else if(problem.equals("B")) { //Remove Not Existing Relations

				if(type.equals("news")) filenamePrefix = path + "News_B"; else filenamePrefix = path + "Photostory";
				
				List<Content> temporary = new ArrayList<Content>();
				
				for(Content c : contentService.getContentsByTypeContentProblemRelationInlineProblem(type, ContentProblem.MISSING_RELATIONS, null, "excludeEverything")) {
					
					Set<Relation> relations = c.getRelationSet();
					Iterator<Relation> iterator = relations.iterator();
					
					int all = relations.size();
					int excluded = 0;
					
					while(iterator.hasNext()) {
						
						Relation current = iterator.next();
						
						if(current.getContentProblem() != null && current.getContentProblem().equals(ContentProblem.MISSING_RELATIONS)) {
							
							iterator.remove();
							excluded++;
						}
					}
					
					c.setRelationSet(relations); //Some Relations Excluded
					c.setDull("_" + excluded + "of" + all + "Excluded");
					
					temporary.add(c);
				}
				
				contentsList.addAll(temporary);
			}
			else if(problem.equals("C")) { //Replace Duplicate Inline Relations With Anchors

				filenamePrefix = path + "News_C";

				for(Content c : contentService.getContentsByTypeContentProblemRelationInlineProblem(type, null, RelationInlineProblem.RELATIONS_CAN_BE_REPLACED, "excludeAuthors")) {
			
					Content temporaryContent = contentService.replaceDuplicateRelationsInlineWithAnchors(c);
					
					if(temporaryContent != null) {
						
						contentsList.add(temporaryContent);
					}
					else {
						
						cannotCorrectDuplicates++;
					}
				}
			}
			else if(problem.equals("D")) { //Remove Not Existing Relations and Replace Duplicate Inline Relations With Anchors
				
				filenamePrefix = path + "News_D";
				
				List<Content> temporaryList = new ArrayList<Content>();
				
				for(Content c : contentService.getContentsByTypeContentProblemRelationInlineProblem(type, ContentProblem.MISSING_RELATIONS, RelationInlineProblem.RELATIONS_CAN_BE_REPLACED, "excludeAuthors")) {
					
					Content temporaryContent = contentService.replaceDuplicateRelationsInlineWithAnchors(c);
					
					if(temporaryContent != null) {
						
						Set<Relation> relations = temporaryContent.getRelationSet();
						Iterator<Relation> iterator = relations.iterator();
						
						int all = relations.size();
						int excluded = 0;
						
						while(iterator.hasNext()) {
							
							Relation current = iterator.next();
							
							if(current.getContentProblem() != null && current.getContentProblem().equals(ContentProblem.MISSING_RELATIONS)) {
								
								iterator.remove();
								excluded++;
							}
						}
						
						temporaryContent.setRelationSet(relations); //Some Relations Excluded
						temporaryContent.setDull("_" + excluded + "of" + all + "Excluded");
						
						temporaryList.add(temporaryContent);
					}
					else {
						
						cannotCorrectDuplicates++;
					}
				}
				
				contentsList.addAll(temporaryList);
			}
			else {
				
				throw new CustomException("Marshalling Controller Can Not Handle The Given Problem Type For 'news'");
			}
		}
		else { //Other 'type' Given
			
			throw new CustomException("Marshalling Controller Can Not Handle The Given Content Type");
		}

		/*
		 * Marshall Contents
		 */
		Escenic escenic = new Escenic();
		escenic.setVersion("2.0");
		Iterator<Content> iterator = contentsList.iterator();
		List<Content> escenicContents = new ArrayList<Content>();
		
		while(iterator.hasNext()) {
		
			Content current = iterator.next();
			escenicContents.add(current);
			contentCounter++;
			
			if(contentCounter % itemsPerFile == 0 || contentCounter == contentsList.size()) {
				
				escenic.setContentList(filterOutElementsAndAttributes(escenicContents));
				
				String filename = itemsPerFile == 1 ? filenamePrefix + "_Id" + current.getApplicationId() : filenamePrefix + "_File" + ++fileCounter;
				if(current.getDull()!=null) filename += current.getDull() + ".xml"; else filename += ".xml";
				
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
		            
		            marshaller.marshal(escenic, result);
				} 
				catch(FileNotFoundException exception) {
					
					throw new CustomException("Make Sure That ${syndicationFiles}/write/ Folder Exists");
				}
				
				//Before Continuing Create A New Escenic Object
				escenic = new Escenic();
				escenic.setVersion("2.0");
				escenicContents = new ArrayList<Content>();
			}
		}

		ModelAndView model = new ModelAndView("administrator/results");
		//Given Values
		model.addObject("random", random);
		model.addObject("type", type);
		model.addObject("problem", problem);
		model.addObject("itemsPerFile", itemsPerFile);
		//Result Values
		model.addObject("marshalled", contentsList.size());
		model.addObject("fileCounter", fileCounter);
		model.addObject("cannotCorrectDuplicates", cannotCorrectDuplicates);

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