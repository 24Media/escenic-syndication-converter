package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.service.AdministratorService;
import gr.twentyfourmedia.syndication.service.AnchorInlineService;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.RelationInlineService;
import gr.twentyfourmedia.syndication.service.RelationService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
		
		ModelAndView model = new ModelAndView("/home");
		return model;
	}

	//TODO Method. ContentWithAnchorsInline Is Created
	@RequestMapping(value = "anchors")
	public ModelAndView anchors() {
		
		anchorInlineService.deleteAllAnchorsInline(); //No Way To Check For Duplicates So Existing Database Entries Must Be Deleted
		administratorService.parseInlineAnchors(5, "cosmo");
		
		ModelAndView model = new ModelAndView("/home");
		return model;
	}
	
	@RequestMapping(value = "tmp")
	public ModelAndView tmp() {
		
		/*
		Content content = contentService.getContent((long) 73, null);
		String body = contentService.getContentBodyFieldField(content).replaceAll("<!\\[CDATA\\[", "").replaceAll("\\]\\]>", "");
		Element jsoup = Jsoup.parse(body);
		Elements relations = jsoup.select("relation");
		
		for(Element r : relations) {
			
			System.out.println(r.attr("source"));
			System.out.println(r.attr("sourceid"));
			System.out.println(r.outerHtml());
		}
		*/
		
		ModelAndView model = new ModelAndView("/home");
		return model;		
	}
}