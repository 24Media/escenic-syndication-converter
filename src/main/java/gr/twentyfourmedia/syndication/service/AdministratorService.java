package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.Content;

public interface AdministratorService {

	void parseInlineRelations();
	
	void findMissingRelations();
	
	void findMissingInlineRelations();
	
	void findDuplicateInlineRelations();
	
	void parseInlineAnchors(int publicationId, String ident);
	
	void parseBodyPersistRelationsInline(Content content, String body);
	
	void parseBodyPersistAnchorsInline(Content content, int publicationId, String ident);
	
	String getContentFromRSSFeed(int publicationId, Long articleId, String ident); //TODO Delete Method. Not Used Anymore
}