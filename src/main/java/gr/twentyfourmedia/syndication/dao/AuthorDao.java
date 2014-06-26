package gr.twentyfourmedia.syndication.dao;

import gr.twentyfourmedia.syndication.model.Author;
import gr.twentyfourmedia.syndication.model.Content;

public interface AuthorDao extends AbstractDao<Author> {

	void persistContentAuthors(Content content);
	
	void mergeContentAuthors(Content content);
}
