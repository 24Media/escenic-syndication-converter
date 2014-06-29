package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.RelationCheck;

import java.util.List;

public interface RelationCheckService {

	void persistRelationCheckEntry(Long contentApplicationId, String contentType, String contentHomeSection, String source, String sourceId, String relationType);
	
	void updateRelationCheckPictureEntry(String sourceId, String pictureBinaryName);
	
	List<RelationCheck> getRelationChecks();
	
	List<String> getDistinctSourceId();
	
	void deleteRelationCheckTable();
}
