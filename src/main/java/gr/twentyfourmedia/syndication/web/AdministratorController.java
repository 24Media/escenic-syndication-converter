package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.service.AdministratorService;
import gr.twentyfourmedia.syndication.service.AnchorInlineService;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.RelationInlineService;
import gr.twentyfourmedia.syndication.service.RelationService;

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
	private RelationService relationService;
	
	@Autowired
	private RelationInlineService relationInlineService;
	
	@Autowired
	private AnchorInlineService anchorInlineService;
	
	@Autowired
	private AdministratorService administratorService;

	@RequestMapping(value = "relations")
	public ModelAndView relations() {

		/*
		 * Clear Existing Values To Examine Contents Again
		 */
		contentService.clearContentProblems();
		relationService.clearRelationProblems();
		relationInlineService.deleteAllRelationsInline();
		
		/*
		 * Order Of Actions Does Matter
		 */
		contentService.excludeContentDraftOrDeleted();
		administratorService.findMissingRelations();
		administratorService.parseInlineRelations();
		administratorService.findDuplicateInlineRelations();
		administratorService.findMissingInlineRelations();

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
}