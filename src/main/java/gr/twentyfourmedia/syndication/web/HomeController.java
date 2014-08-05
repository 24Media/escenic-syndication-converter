package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.SectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		
		ModelAndView model = new ModelAndView("home");
		
		Long countSection = sectionService.countSection();
		Long countTag = contentService.countContentByType("tag");
		Long countPicture = contentService.countContentByType("picture");
		Long countMultipleTypeVideo = contentService.countContentByType("multipleTypeVideo");
		
		model.addObject("countSection", countSection);
		model.addObject("countTag", countTag);
		model.addObject("countPicture", countPicture);
		model.addObject("countMultipleTypeVideo", countMultipleTypeVideo);

		return model;
	}
}
