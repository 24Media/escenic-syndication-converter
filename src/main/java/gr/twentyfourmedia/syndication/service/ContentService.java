package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.Content;

import java.util.List;

public interface ContentService {

	void persistContent(Content content);
	
	void mergeContent(Content content);
	
	Content getFilteredContent(Long id);
	
	Content getFilteredContent(String sourceId);
	
	String getContentHomeSection(Content content);
	
	String getPictureContentBinaryName(Content content);
	
	List<Content> getFilteredContents();
	
	List<Content> getFilteredContentsByType(String type);
	
	List<Content> getContentsByTypeAndHomeSections(String type, List<String> homeSections);
	
	List<Content> getContentsByTypeExcludingHomeSections(String type, List<String> homeSections);
	
	void handleContentHTMLFields(Content content, String path);
	
	String getFieldHTMLContent(String path, String htmlField, String contentSourceId, String relationSourceId);
}