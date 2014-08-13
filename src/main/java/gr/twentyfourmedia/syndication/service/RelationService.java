package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.Relation;

public interface RelationService {

	void persistRelation(Relation relation);
	
	void mergeRelation(Relation relation);
	
	void clearRelationProblems();
}