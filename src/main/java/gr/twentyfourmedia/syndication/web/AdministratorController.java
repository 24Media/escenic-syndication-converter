package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Field;
import gr.twentyfourmedia.syndication.model.Relation;
import gr.twentyfourmedia.syndication.model.RelationCheck;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.FieldService;
import gr.twentyfourmedia.syndication.service.RelationCheckService;
import gr.twentyfourmedia.syndication.service.RelationService;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

	@Autowired
	private ContentService contentService;
	
	@Autowired
	private FieldService fieldService;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private RelationCheckService relationCheckService;
	
	@RequestMapping(value = "relationCheck")
	public String relationList(Model model) {
		
		/*
		 * Re-Calculate Relations
		 */
		relationCheckService.deleteRelationCheckTable();
		persistRelations();
		persistInlineRelations();
		persistPictureBinaryNames();

		/*
		 * Add Attributes To Model
		 */
		List<RelationCheck> relations = relationCheckService.getRelationChecks();
		
		int nullRelations = 0;
		
		for(RelationCheck r : relations) {
			if(r.getRelatedContentType() == null) {
				nullRelations++;
			}
		}
		
		model.addAttribute("relations", relations);
		model.addAttribute("nullRelations", nullRelations);
		
		return "/administrator/relations";
	}
	
	private void persistRelations() {
		
		List<Relation> relations = relationService.getRelations();
		
		for(Relation r : relations) {
		
			relationCheckService.persistRelationCheckEntry(r.getContentApplicationId().getApplicationId(), r.getContentApplicationId().getType(), contentService.getContentHomeSection(r.getContentApplicationId()), r.getSource(), r.getSourceId(), r.getType());
		}
	}
	
	private void persistInlineRelations() {
		
		List<Field> fields = fieldService.getFieldsByBodyContaining("<relation ");
		
		for(Field f : fields) {
		
			parseBodyFieldAndPersist(f.getContentApplicationId(), f.getField());
		}
	}
	
	private void persistPictureBinaryNames() {
		
		List<String> sourceIds = relationCheckService.getDistinctSourceId();
		String path = System.getProperty("filepath.syndicationFiles") + "/images/";
		
		for(String s : sourceIds) {
			
			String pictureBinaryName = contentService.getPictureContentBinaryName(contentService.getContent(s));
			if(pictureBinaryName != null) {
				
				File file = new File(path + pictureBinaryName);
				String pictureBinaryExists = file.exists() ? "YES" : "NO";
				relationCheckService.updateRelationCheckPictureEntry(s, pictureBinaryExists); //May Update More Than One Entries
			}
		}
	}
	
	/**
	 * Persist RelationCheck Entry. All Body Field Relations Are Consider To Have A New Type : INLINE 
	 * @param contentApplicationId Content's Id
	 * @param contentType Content's Type
	 * @param body Body Field
	 */
	private void parseBodyFieldAndPersist(Content content, String body) {
		
		String input = body;
		String split = "<relation ";
		String source = "source=\"";
		String sourceId = "sourceid=\"";
		
		while(input.indexOf(split) > -1) { //Body Field Has More Relations
			
			input = input.substring(input.indexOf(split)+10);
			
			//Keep Two Temporary Variables Because You Cannot Be Sure Which Comes First
			String temporarySource = input.substring(input.indexOf(source)+8);
			String temporarySourceId = input.substring(input.indexOf(sourceId)+10);
			
			relationCheckService.persistRelationCheckEntry(content.getApplicationId(), content.getType(), contentService.getContentHomeSection(content), temporarySource.substring(0, temporarySource.indexOf("\"")), temporarySourceId.substring(0, temporarySourceId.indexOf("\"")), "INLINE");
		}
	}
}
