package gr.twentyfourmedia.syndication.dao;

import gr.twentyfourmedia.syndication.model.RelationCheck;

import java.util.List;

public interface RelationCheckDao {

	void persistEntry(Long contentApplicationId, String contentType, String contentHomeSection, String source, String sourceId, String relationType); 
	
	List<RelationCheck> getEntries();
	
	void deleteTable();
}
