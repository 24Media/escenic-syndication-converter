package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.service.ParentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/parent")
public class ParentController {

	@Autowired
	private ParentService parentService;
	
	@RequestMapping(value = "list/{applicationId}", method = RequestMethod.GET)
	public String list(@PathVariable("applicationId") Long applicationId, Model model) {

		model.addAttribute("parent", parentService.getParent(applicationId));
		return "parent/list";
	}
}
