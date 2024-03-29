package gr.twentyfourmedia.syndication.dao;

import java.util.List;
import java.util.Map;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.ContentProblem;
import gr.twentyfourmedia.syndication.model.RelationInlineProblem;

public interface ContentDao extends AbstractDao<Content> {

	Content get(Long applicationId, String filterName);
	
	Content getBySourceId(String sourceId, String filterName);
	
	Content getRandom(String filterName);
	
	List<Content> getByType(String type, String filterName);
	
	List<Content> getWithRelationsInline(String filterName);
	
	List<Content> getWithAnchorsInline(String filterName);
	
	List<Content> getByContentProblem(ContentProblem contentProblem, String filterName);
	
	List<Content> getByRelationInlineProblem(RelationInlineProblem relationInlineProblem, String filterName);
	
	List<Content> getByTypeContentProblemRelationInlineProblem(String type, ContentProblem contentProblem, RelationInlineProblem relationInlineProblem, String filterName);
	
	List<Content> getExcludingContentProblemsIncludingRelationInlineProblems(List<ContentProblem> contentProblems, List<RelationInlineProblem> relationInlineProblems, String filterName);

	void exclude(ContentProblem contentProblem, List<Long> applicationId);
	
	void excludeByStates(ContentProblem contentProblem, List<String> states);
	
	void clearProblems();
	
	void clearDuplicates();
	
	Map<String, Map<String, Long>> summary(String namedQuery);
		
	Map<String, Map<String, Long>> combinedSummary();
	
	Long countByType(String type);
}
