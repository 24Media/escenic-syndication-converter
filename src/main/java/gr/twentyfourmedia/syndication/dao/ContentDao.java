package gr.twentyfourmedia.syndication.dao;

import java.util.List;

import gr.twentyfourmedia.syndication.model.Content;

public interface ContentDao extends AbstractDao<Content> {

	Content getBySourceId(String sourceId);
	
	List<Content> getByType(String type);
}
