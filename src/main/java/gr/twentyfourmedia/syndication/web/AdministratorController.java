package gr.twentyfourmedia.syndication.web;

import java.util.List;

import gr.twentyfourmedia.syndication.model.Content;
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
			
			System.out.println("x");
		}
		
		//TODO Method
		return "/home";
	}
	
	@RequestMapping(value = "/article/", method = RequestMethod.GET)
	public String readRSSFeed(	@RequestParam(value = "publicationId", required = true) Long publicationId,
								@RequestParam(value = "articleId", required = true) Long articleId, 
								@RequestParam(value = "ident", required = true) String ident,
								Model model) {
		
		String article = administratorService.getUrlContent(publicationId, articleId, ident);
		
		System.out.println(article);
		
		//administratorService.patternMatcher(article);
		
		return "/home";
	}
}