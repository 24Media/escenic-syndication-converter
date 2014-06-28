package gr.twentyfourmedia.syndication.dao;

public interface RelationCheckDao {

	void persistEntry(Long contentApplicationId, String source, String sourceId, String type); 
	
	void deleteTable();
}
