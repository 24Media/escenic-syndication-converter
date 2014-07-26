package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.service.AdministratorService;
import gr.twentyfourmedia.syndication.service.AnchorInlineService;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.RelationInlineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

	@Autowired
	private ContentService contentService;
	
	@Autowired
	private RelationInlineService relationInlineService;
	
	@Autowired
	private AnchorInlineService anchorInlineService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@RequestMapping(value = "parseInlineRelations")
	public ModelAndView parseInlineRelations() {
		
		relationInlineService.deleteAllRelationsInline(); //Duplicates Are Possible So Existing Database Entries Must Be Deleted
		administratorService.parseInlineRelations();
		
		ModelAndView model = new ModelAndView("/home");
		return model;
	}
	
	@RequestMapping(value = "parseInlineAnchors")
	public ModelAndView parseInlineAnchors() {
		
		anchorInlineService.deleteAllAnchorsInline(); //No Way To Check For Duplicates So Existing Database Entries Must Be Deleted
		administratorService.parseInlineAnchors(5, "cosmo");
		
		ModelAndView model = new ModelAndView("/home");
		return model;
	}	
	
	@RequestMapping(value = "findMissingRelations")
	public ModelAndView findMissingRelations() {
		
		contentService.excludeContentDraftOrDeleted(); //Before Content Relation Looping Exclude 'draft' or 'deleted'
		
		
		administratorService.findMissingInlineRelations();
		
		
		//TODO Controller
		ModelAndView model = new ModelAndView("/home");
		return model;
	}

	@RequestMapping(value = "findDuplicateInlineRelations")
	public ModelAndView findDuplicateInlineRelations() {
		
		administratorService.findDuplicateInlineRelations();
		
		ModelAndView model = new ModelAndView("/home");
		return model;
	}
}