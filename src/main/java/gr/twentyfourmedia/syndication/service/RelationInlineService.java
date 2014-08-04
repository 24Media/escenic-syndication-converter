package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.RelationInline;
import gr.twentyfourmedia.syndication.model.RelationInlineProblem;

public interface RelationInlineService {

	void persistRelationInline(RelationInline relationInline);
	
	void mergeRelationInline(RelationInline relationInline);
	
	void deleteAllRelationsInline();
	
	int countRelationsInlineWithGivenProblem(Content content, RelationInlineProblem relationInlineProblem);
}
