package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.Content;

import java.util.List;

public interface ContentService {

	void persistContent(Content content);
	
	void mergeContent(Content content);
	
	List<Content> getContents();
}
