package gr.twentyfourmedia.syndication.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.AuthorDao;
import gr.twentyfourmedia.syndication.model.Author;
import gr.twentyfourmedia.syndication.model.Content;

@Repository
public class HibernateAuthorDao extends HibernateAbstractDao<Author> implements AuthorDao {

	@Override
	public void persistContentAuthors(Content content) {

		for(Author a : content.getAuthorSet()) {

			a.setContentApplicationId(content);
			persist(a);
		}
	}
	
	@Override
	public void mergeContentAuthors(Content content) {

		for(Author a : content.getAuthorSet()) {

			a.setContentApplicationId(content);
			merge(a);
		}
	}
}
