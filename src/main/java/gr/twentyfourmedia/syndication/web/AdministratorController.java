package gr.twentyfourmedia.syndication.web;

import java.util.Iterator;
import java.util.List;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.ContentProblem;
import gr.twentyfourmedia.syndication.model.RelationInline;
import gr.twentyfourmedia.syndication.model.RelationInlineProblem;
import gr.twentyfourmedia.syndication.service.AdministratorService;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.RelationInlineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

	@Autowired
	private ContentService contentService;
	
	@Autowired
	private RelationInlineService relationInlineService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@RequestMapping(value = "parseInlineRelations")
	public String parseInlineRelations(Model model) {
		
		relationInlineService.deleteAllRelationsInline();
		administratorService.parseInlineRelations();
		
		return "/home";
	}
	
	@RequestMapping(value = "findMissingRelations")
	public String findMissingRelations(Model model) {
		
		//TODO Method
		return "/home";
	}
	
	@RequestMapping(value = "findDuplicateInlineRelations")
	public String findDuplicateInlineRelations(Model model) {
	
		List<Content> contents = contentService.getFilteredContentsWithRelationsInline();
		
		for(Content c : contents) {
			
			boolean duplicatesFound = false;
			String previousSourceId = null;
			
			Iterator<RelationInline> iterator = c.getRelationInlineSet().iterator();
		    
			while(iterator.hasNext()) {
			
				RelationInline relationInline = iterator.next();
				String currentSourceId = relationInline.getSourceId();
				
				if(currentSourceId != null && currentSourceId.equals(previousSourceId)) { //Duplicate
					
					relationInline.setRelationInlineProblem(RelationInlineProblem.RELATION_NEEDS_REPLACEMENT);
					relationInlineService.mergeRelationInline(relationInline);
					duplicatesFound = true; //Content Entity Needs Change Too
				}
					
				previousSourceId = currentSourceId; //Change Previous Value Before Continuing
			}
			
			if(duplicatesFound) {
				
				c.setContentProblem(ContentProblem.DUPLICATE_INLINE_RELATIONS);
				contentService.mergeContent(c, false);
			}
		}

		return "/home";
	}
	
	@RequestMapping(value = "/article/", method = RequestMethod.GET)
	public String readRSSFeed(	@RequestParam(value = "publicationId", required = true) Long publicationId,
								@RequestParam(value = "articleId", required = true) Long articleId, 
								@RequestParam(value = "ident", required = true) String ident,
								Model model) {
		
		String article = administratorService.getUrlContent(publicationId, articleId, ident);
		
		System.out.println(article);
		//TODO Method
		//administratorService.patternMatcher(article);
		
		return "/home";
	}
}