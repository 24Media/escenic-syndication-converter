package gr.twentyfourmedia.syndication.service;

public interface RelationCheckService {

	void persisteRelationCheckEntry(Long contentApplicationId, String source, String sourceId, String type);
	
	void deleteRelationCheckTable();
}
