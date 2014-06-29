package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.RelationCheck;

import java.util.List;

public interface RelationCheckService {

	void persistRelationCheckEntry(Long contentApplicationId, String contentType, String contentHomeSection, String source, String sourceId, String relationType);
	
	List<RelationCheck> getRelationChecks();
	
	void deleteRelationCheckTable();
}
