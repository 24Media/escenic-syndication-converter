package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.service.AdministratorService;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.RelationInlineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView parseInlineRelations() {
		
		relationInlineService.deleteAllRelationsInline(); //Duplicates Are Possible So Existing Database Entries Must Be Deleted
		administratorService.parseInlineRelations();
		
		ModelAndView model = new ModelAndView("/home");
		return model;
	}
	
	@RequestMapping(value = "findMissingRelations")
	public String findMissingRelations(Model model) {
		
		//TODO Controller
		return "/home";
	}

	@RequestMapping(value = "findDuplicateInlineRelations")
	public ModelAndView findDuplicateInlineRelations() {
		
		administratorService.findDuplicateInlineRelations();
		
		ModelAndView model = new ModelAndView("/home");
		return model;
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