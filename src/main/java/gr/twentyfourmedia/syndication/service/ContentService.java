package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.ContentProblem;
import gr.twentyfourmedia.syndication.model.Field;
import gr.twentyfourmedia.syndication.model.RelationInlineProblem;

import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

public interface ContentService {

	void persistContent(Content content);
	
	void mergeContent(Content content, boolean initialize);
	
	Content getContent(Long applicationId, String filterName);
	
	Content getContent(String sourceId, String filterName);
	
	Content getRandomContent(String filterName);
	
	Field getContentField(Content content, String fieldName);
	
	String getContentFieldField(Content content, String fieldName);
	
	List<Content> getContentsByType(String type, String filterName);

	List<Content> getContentsWithRelationsInline(String filterName);

	List<Content> getContentsWithAnchorsInline(String filterName);	
	
	List<Content> getContentsByContentProblem(ContentProblem contentProblem, String filterName);
	
	List<Content> getContentsByRelationInlineProblem(RelationInlineProblem relationInlineProblem, String filterName);
	
	List<Content> getContentsByTypeContentProblemRelationInlineProblem(String type, ContentProblem contentProblem, RelationInlineProblem relationInlineProblem, String filterName);
	
	List<Content> getContentsExcludingContentProblemsIncludingRelationInlineProblems(List<ContentProblem> contentProblems, List<RelationInlineProblem> relationInlineProblems, String filterName);
	
	void handleContentHTMLFields(Content content, String path) throws DocumentException;
	
	String getFieldHTMLContent(String path, String htmlField, String contentSourceId, String relationSourceId) throws DocumentException;
	
	boolean contentExists(String sourceId);
	
	void excludeContentByTypeAndHomeSections(String type, String description);
	
	void excludeContentByStates(ContentProblem contentProblem, List<String> states);
	
	void clearContentProblems();
	
	void clearContentDuplicates();
	
	void updateContentProblem(Content content, ContentProblem contentProblem);
	
	void deleteContent(Long applicationId);
	
	Content replaceDuplicateRelationsInlineWithAnchors(Content content);
	
	Map<String, Map<String, Long>> contentSummary(String namedQuery);
	
	Map<String, Map<String, Long>> contentCombinedSummary();
	
	Long countContentByType(String type);
}