package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.RelationInline;

import java.util.Set;

public interface AdministratorService {

	void parseInlineRelations();
	
	void findMissingRelations();
	
	void findMissingInlineRelations();
	
	void findDuplicateInlineRelations();
	
	Set<RelationInline> parseBodyPersistRelationsInline(Content content, String body);
	
	void parseRSSFeedPersistAnchors(String search);
	
	String getUrlContent(Long publicationId, Long articleId, String ident);
}
