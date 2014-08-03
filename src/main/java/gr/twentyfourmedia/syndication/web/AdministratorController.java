package gr.twentyfourmedia.syndication.web;

import java.util.Map;

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
		
		ModelAndView model = new ModelAndView("/administrator/relations");
		populateSummaries(model);
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
		
		ModelAndView model = new ModelAndView("/administrator/anchors");
		populateSummaries(model);
		return model;
	}
	
	/**
	 * Just Populate Summaries And Show The Results
	 * @return ModelAndView Object
	 */
	@RequestMapping(value = "analysis")
	public ModelAndView analysis() {

		ModelAndView model = new ModelAndView("/administrator/anchors");
		populateSummaries(model);
		return model;
	}
	
	/**
	 * Add Content and Relation Inline Problems Summaries To Given ModelAndView Object
	 * @param model ModelAndView Object
	 * @return ModelAndView Enriched With Summaries
	 */
	private ModelAndView populateSummaries(ModelAndView model) {
		
		/*
		 * Content Problems Summary
		 */
		Map<String, Map<String, Long>> problems = contentService.contentSummary("problemSummary");
		Map<String, Long> pictureProblems = problems.get("picture");
		Map<String, Long> multipleTypeVideoProblems = problems.get("multipleTypeVideo");
		Map<String, Long> tagProblems = problems.get("tag");
		Map<String, Long> photostoryProblems = problems.get("photostory");
		Map<String, Long> newsProblems = problems.get("news");
		model.addObject("pictureProblems", pictureProblems);
		model.addObject("multipleTypeVideoProblems", multipleTypeVideoProblems);
		model.addObject("tagProblems", tagProblems);
		model.addObject("photostoryProblems", photostoryProblems);
		model.addObject("newsProblems", newsProblems);

		/*
		 * Content Duplicates Summary
		 */
		Map<String, Map<String, Long>> duplicates = contentService.contentSummary("relationInlineSummary");
		Map<String, Long> newsDuplicates = duplicates.get("news");
		model.addObject("newsDuplicates", newsDuplicates);

		/*
		 * Content Combined Summary
		 */
		Map<String, Map<String, Long>> combinedProblems = contentService.contentCombinedSummary();
		model.addObject("combinedProblems", combinedProblems);
		
		return model;
	}
}