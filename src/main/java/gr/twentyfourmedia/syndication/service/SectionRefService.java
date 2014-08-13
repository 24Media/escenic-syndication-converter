package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.SectionRef;

public interface SectionRefService {

	void persistSectionRef(SectionRef sectionRef);
	
	void mergeSectionRef(SectionRef sectionRef);
}
