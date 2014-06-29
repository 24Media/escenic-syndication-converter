package gr.twentyfourmedia.syndication.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.ContentDao;
import gr.twentyfourmedia.syndication.model.Content;

@Repository
public class HibernateContentDao extends HibernateAbstractDao<Content> implements ContentDao {

	@Override
	public Content getBySourceId(String sourceId) {
		
		Query query = getSession().getNamedQuery("findContentBySourceId");
		query.setParameter("sourceId", sourceId);
		return (Content) query.uniqueResult();		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Content> getByType(String type) {
		
		Query query = getSession().getNamedQuery("findContentsByType");
		query.setParameter("type", type);
		return (List<Content>) query.list();
	}
}
