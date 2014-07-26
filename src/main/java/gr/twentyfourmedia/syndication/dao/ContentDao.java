package gr.twentyfourmedia.syndication.dao;

import java.util.List;

import gr.twentyfourmedia.syndication.model.Content;

public interface ContentDao extends AbstractDao<Content> {

	Content get(Long id, String filterName);
	
	Content getBySourceId(String sourceId, String filterName);
	
	List<Content> get(String filterName);
	
	List<Content> getByType(String type, String filterName);
	
	List<Content> getWithRelationsInline(String filterName);
}
