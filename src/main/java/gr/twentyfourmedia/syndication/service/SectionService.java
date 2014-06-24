package gr.twentyfourmedia.syndication.service;

import java.util.List;

import gr.twentyfourmedia.syndication.model.Section;

public interface SectionService {

	void persistSection(Section section);
	
	void mergeSection(Section section);
	
	List<Section> getSections();
}
