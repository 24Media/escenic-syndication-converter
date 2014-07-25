package gr.twentyfourmedia.syndication.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.ContentDao;
import gr.twentyfourmedia.syndication.model.Content;

@Repository
public class HibernateContentDao extends HibernateAbstractDao<Content> implements ContentDao {

	@Override
	public Content getFiltered(Long id) {
		
		getSession().enableFilter("excludeAuthors");
		return get(id);
	}
	
	@Override
	public Content getFilteredBySourceId(String sourceId) {
		
		getSession().enableFilter("excludeAuthors");
		Query query = getSession().getNamedQuery("findContentBySourceId");
		query.setParameter("sourceId", sourceId);
		return (Content) query.uniqueResult();
	}
	
	@Override
	public List<Content> getFiltered() {
		
		getSession().enableFilter("excludeAuthors");
		return getAll();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Content> getFilteredByType(String type) {
		
		getSession().enableFilter("excludeAuthors");
		Query query = getSession().getNamedQuery("findContentsByType");
		query.setParameter("type", type);
		return (List<Content>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> getFilteredWithRelationsInline() {

		getSession().enableFilter("excludeAuthors");
		Query query = getSession().getNamedQuery("findContentsWithRelationsInline");
		return (List<Content>) query.list();
	}
}