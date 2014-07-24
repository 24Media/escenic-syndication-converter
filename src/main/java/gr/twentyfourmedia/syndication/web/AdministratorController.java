package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.service.AdministratorService;
import gr.twentyfourmedia.syndication.service.RelationInlineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

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
}