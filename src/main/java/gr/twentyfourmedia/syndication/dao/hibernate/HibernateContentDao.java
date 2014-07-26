package gr.twentyfourmedia.syndication.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.ContentDao;
import gr.twentyfourmedia.syndication.model.Content;

@Repository
public class HibernateContentDao extends HibernateAbstractDao<Content> implements ContentDao {

	@Override
	public Content get(Long id, String filterName) {
		
		if(filterName != null) getSession().enableFilter(filterName);
		return get(id);
	}
	
	@Override
	public Content getBySourceId(String sourceId, String filterName) {
		
		if(filterName != null) getSession().enableFilter(filterName);
		Query query = getSession().getNamedQuery("findContentBySourceId");
		query.setParameter("sourceId", sourceId);
		return (Content) query.uniqueResult();
	}
	
	@Override
	public List<Content> get(String filterName) {
		
		if(filterName != null) getSession().enableFilter(filterName);
		return getAll();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Content> getByType(String type, String filterName) {
		
		if(filterName != null) getSession().enableFilter(filterName);
		Query query = getSession().getNamedQuery("findContentsByType");
		query.setParameter("type", type);
		return (List<Content>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> getWithRelationsInline(String filterName) {

		if(filterName != null) getSession().enableFilter(filterName);
		Query query = getSession().getNamedQuery("findContentsWithRelationsInline");
		return (List<Content>) query.list();
	}
}