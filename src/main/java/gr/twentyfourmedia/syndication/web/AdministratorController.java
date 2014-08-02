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
		contentService.excludeContentByTypeAndHomeSections("news", "kairos");
		contentService.excludeContentByStateDraftOrDeleted();
		administratorService.findMissingRelations();
		administratorService.parseInlineRelations();
		administratorService.findMissingInlineRelations();
		administratorService.findDuplicateInlineRelations();
		
		ModelAndView model = new ModelAndView("redirect:/");
		return model;
	}

	@RequestMapping(value = "anchors")
	public ModelAndView anchors() {
		
		/*
		 * Clear Existing Values To Examine Contents Again
		 */
		anchorInlineService.deleteAllAnchorsInline();
		
		administratorService.parseInlineAnchors(5, "cosmo");
		administratorService.characterizeContentAndRelationsInline();
			
		ModelAndView model = new ModelAndView("redirect:/");
		return model;
	}
}