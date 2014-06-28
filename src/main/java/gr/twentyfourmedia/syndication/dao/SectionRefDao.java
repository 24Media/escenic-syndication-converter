package gr.twentyfourmedia.syndication.dao;

import java.util.List;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.SectionRef;

public interface SectionRefDao extends AbstractDao<SectionRef> {

	void persistContentSectionRefs(Content content);
	
	void mergeContentSectionRefs(Content content);
	
	List<SectionRef> getByHomeSections(List<String> homeSections);
	
	List<SectionRef> getExcludingHomeSections(List<String> homeSections);
}
