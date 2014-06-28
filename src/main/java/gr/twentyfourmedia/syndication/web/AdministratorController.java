package gr.twentyfourmedia.syndication.web;

import gr.twentyfourmedia.syndication.model.Field;
import gr.twentyfourmedia.syndication.service.FieldService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

	@Autowired
	private FieldService fieldService;	
	
	@RequestMapping(value = "relations")
	public String relations(Model model) {

		List<Field> fields = fieldService.getFieldsByBodyContaining("<relation ");
		
		System.out.println(fields.size());
		
		for(Field f : fields) {
			
			System.out.println(f.getContentApplicationId().getApplicationId());
			parseBody(f.getField());
			
			//break;
		}
		
		
		return "/home";
	}
	
	public void parseBody(String body) {
		
		String input = body;
		String split = "<relation ";
		String source = "source=\"";
		String sourceId = "sourceid=\"";
		
		
		while(input.indexOf(split) > -1) {
			
			input = input.substring(input.indexOf(split)+10);
			
			//Keep Two Temporary Variables Because You Cannot Be Sure Which Comes First
			String temporarySource = input.substring(input.indexOf(source)+8);
			String temporarySourceId = input.substring(input.indexOf(sourceId)+10);
			
			
			
			System.out.println(temporarySource.substring(0, temporarySource.indexOf("\"")));
			System.out.println(temporarySourceId.substring(0, temporarySourceId.indexOf("\"")));
		}
		
		
	}
}
