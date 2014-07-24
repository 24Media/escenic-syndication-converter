package gr.twentyfourmedia.syndication.service.implementation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.RelationInline;
import gr.twentyfourmedia.syndication.service.AdministratorService;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.RelationInlineService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdministratorServiceImplementation implements AdministratorService {

	@Autowired
	private ContentService contentService;
	
	@Autowired
	private RelationInlineService relationInlineService;
	
	@Override
	public void parseInlineRelations() {
	
		List<Content> contents = contentService.getFilteredContentsByType("news");
		
		for(Content c : contents) {
			
			String body = contentService.getContentHomeFieldField(c);
			
			if(body != null) {
			
				Set<RelationInline> relationsInlineSet = parseBodyPersistRelationsInline(c, body);
				
				if(relationsInlineSet.size()>0) {
					
					c.setRelationInlineSet(relationsInlineSet);
					contentService.mergeContent(c, false);
				}
			}
		}
	}
	
	/**
	 * Persist Content's Inline Relations
	 * @param content Content
	 * @param body Content's Body Field String
	 * @return Set<RelationsInline> For Given Content
	 */
	@Override
	public Set<RelationInline> parseBodyPersistRelationsInline(Content content, String body) {
		
		String input = body;
		String split = "<relation ";
		String source = "source=\"";
		String sourceId = "sourceid=\"";
		
		Set<RelationInline> relationsInline = new HashSet<RelationInline>();
		
		while(input.indexOf(split) > -1) { //Body Field Has More Relations
			
			input = input.substring(input.indexOf(split)+10);
			
			//Keep Two Temporary Variables Because You Cannot Be Sure Which Comes First
			String temporarySource = input.substring(input.indexOf(source)+8);
			String temporarySourceId = input.substring(input.indexOf(sourceId)+10);
			
			RelationInline relationInline = new RelationInline();
			relationInline.setContentApplicationId(content);
			relationInline.setSource(temporarySource.substring(0, temporarySource.indexOf("\"")));
			relationInline.setSourceId(temporarySourceId.substring(0, temporarySourceId.indexOf("\"")));
			
			relationInlineService.persistRelationInline(relationInline);
			relationsInline.add(relationInline);
		}
		
		return relationsInline;
	}
}