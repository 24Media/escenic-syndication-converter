package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.RelationInline;

public interface RelationInlineService {

	void persistRelationInline(RelationInline relationInline);
	
	void mergeRelationInline(RelationInline relationInline);
	
	void deleteAllRelationsInline();
}
