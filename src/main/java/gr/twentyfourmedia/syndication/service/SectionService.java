package gr.twentyfourmedia.syndication.service;

import java.util.List;

import gr.twentyfourmedia.syndication.model.Section;

public interface SectionService {

	void persistSection(Section section);
	
	void mergeSection(Section section);
	
	Section getSectionByApplicationId(Long applicationId);
	
	Section getSectionBySourceId(String sourceId);
	
	Section getSectionByUniqueNameElement(String uniqueNameElement);
	
	List<Section> getSections();
	
	boolean sectionExistsBySourceId(String sourceId);
	
	boolean sectionExistsByUniqueNameElement(String uniqueNameElement);
	
	void deleteSection(Long applicationId);
	
	Long countSection();
}
