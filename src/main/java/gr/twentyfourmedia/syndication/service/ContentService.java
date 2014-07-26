package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.ContentProblem;
import gr.twentyfourmedia.syndication.model.Field;

import java.util.List;

public interface ContentService {

	void persistContent(Content content);
	
	void mergeContent(Content content, boolean initialize);
	
	Content getContent(Long id, String filterName);
	
	Content getContent(String sourceId, String filterName);
	
	String getContentHomeSection(Content content);
	
	Field getContentHomeField(Content content);
	
	String getContentHomeFieldField(Content content);
	
	String getPictureContentBinaryName(Content content);
	
	List<Content> getContents(String filterName);
	
	List<Content> getContentsByType(String type, String filterName);
	
	List<Content> getContentsWithRelationsInline(String filterName);
	
	List<Content> getContentsByContentProblem(ContentProblem contentProblem, String filterName);
	
	List<Content> getContentsByTypeAndHomeSections(String type, List<String> homeSections);
	
	List<Content> getContentsByTypeExcludingHomeSections(String type, List<String> homeSections);
	
	void handleContentHTMLFields(Content content, String path);
	
	String getFieldHTMLContent(String path, String htmlField, String contentSourceId, String relationSourceId);
	
	boolean contentExists(String sourceId);
	
	void excludeContentDraftOrDeleted();
	
	void updateContentProblem(Content content, ContentProblem contentProblem);
}