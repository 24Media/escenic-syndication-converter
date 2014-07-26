package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.Relation;

import java.util.List;

public interface RelationService {

	void persistRelation(Relation relation);
	
	void mergeRelation(Relation relation);
	
	List<Relation> getRelations();
	
	void clearRelationProblems();
}