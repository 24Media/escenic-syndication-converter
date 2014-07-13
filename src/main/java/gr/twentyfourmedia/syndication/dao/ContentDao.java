package gr.twentyfourmedia.syndication.dao;

import java.util.List;

import gr.twentyfourmedia.syndication.model.Content;

public interface ContentDao extends AbstractDao<Content> {

	Content getFiltered(Long id);
	
	Content getFilteredBySourceId(String sourceId);
	
	List<Content> getFiltered();
	
	List<Content> getFilteredByType(String type);
}
