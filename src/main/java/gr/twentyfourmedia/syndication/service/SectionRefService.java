package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.SectionRef;

import java.util.List;

public interface SectionRefService {

	void persistSectionRef(SectionRef sectionRef);
	
	void mergeSectionRef(SectionRef sectionRef);
	
	List<SectionRef> getSectionRefs();
}
