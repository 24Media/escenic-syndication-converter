package gr.twentyfourmedia.syndication.dao;

import java.util.List;
import java.util.Map;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.ContentProblem;
import gr.twentyfourmedia.syndication.model.RelationInlineProblem;

public interface ContentDao extends AbstractDao<Content> {

	Content get(Long applicationId, String filterName);
	
	Content getBySourceId(String sourceId, String filterName);
	
	List<Content> get(String filterName);
	
	List<Content> getByType(String type, String filterName);
	
	List<Content> getWithRelations(String filterName);
	
	List<Content> getWithRelationsInline(String filterName);
	
	List<Content> getWithAnchorsInline(String filterName);
	
	List<Content> getByContentProblem(ContentProblem contentProblem, String filterName);
	
	List<Content> getByRelationInlineProblem(RelationInlineProblem relationInlineProblem, String filterName);
	
	List<Content> getExcludingContentProblemsIncludingRelationInlineProblem(List<ContentProblem> contentProblems, RelationInlineProblem relationInlineProblem, String filterName);

	void excludeByStateDraftOrDeleted();
	
	void clearProblems();
	
	Map<String, Map<String, Long>> summary(String namedQuery);
		
	Map<String, Map<String, Long>> combinedSummary();
}
