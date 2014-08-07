package gr.twentyfourmedia.syndication.service;

import java.io.IOException;

import gr.twentyfourmedia.syndication.model.Content;

public interface AdministratorService {

	void parseInlineRelations();
	
	void findMissingRelations();
	
	void findMissingInlineRelations();
	
	void findDuplicateInlineRelations();
	
	void parseInlineAnchors(int publicationId, String ident) throws IOException;
	
	void characterizeContentAndRelationsInline();

	void parseBodyPersistRelationsInline(Content content, String body);
	
	void parseBodyPersistAnchorsInline(Content content, int publicationId, String ident) throws IOException;
}
