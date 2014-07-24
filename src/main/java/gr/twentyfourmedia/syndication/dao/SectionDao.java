package gr.twentyfourmedia.syndication.dao;

import gr.twentyfourmedia.syndication.model.Section;

public interface SectionDao extends AbstractDao<Section> {

	Section getBySourceId(String sourceId);
	
	Section getByUniqueNameElement(String uniqueNameElement);
}
