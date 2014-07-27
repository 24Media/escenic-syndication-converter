package gr.twentyfourmedia.syndication.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.ContentDao;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.ContentProblem;
import gr.twentyfourmedia.syndication.model.RelationInlineProblem;

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
	public List<Content> getWithRelations(String filterName) {

		if(filterName != null) getSession().enableFilter(filterName);
		Query query = getSession().getNamedQuery("findContentsWithRelations");
		return (List<Content>) query.list();
	}	

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> getWithRelationsInline(String filterName) {

		if(filterName != null) getSession().enableFilter(filterName);
		Query query = getSession().getNamedQuery("findContentsWithRelationsInline");
		return (List<Content>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> getWithAnchorsInline(String filterName) {

		if(filterName != null) getSession().enableFilter(filterName);
		Query query = getSession().getNamedQuery("findContentsWithAnchorsInline");
		return (List<Content>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> getByContentProblem(ContentProblem contentProblem, String filterName) {
		
		if(filterName != null) getSession().enableFilter(filterName);
		Query query = getSession().getNamedQuery("findContentsByContentProblem");
		query.setParameter("contentProblem", contentProblem);
		return (List<Content>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> getByRelationInlineProblem(RelationInlineProblem relationInlineProblem, String filterName) {
		
		if(filterName != null) getSession().enableFilter(filterName);
		Query query = getSession().getNamedQuery("findContentsByRelationInlineProblem");
		query.setParameter("relationInlineProblem", relationInlineProblem);
		return (List<Content>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> getExcludingContentProblemsIncludingRelationInlineProblem(List<ContentProblem> contentProblems, RelationInlineProblem relationInlineProblem, String filterName) {
	
		if(filterName != null) getSession().enableFilter(filterName);
		Query query = getSession().getNamedQuery("findContentsExcludingContentProblemsIncludingRelationInlineProblem");
		query.setParameterList("contentProblem", contentProblems);
		query.setParameter("relationInlineProblem", relationInlineProblem);
		return (List<Content>) query.list();
	}
	
	@Override
	public void excludeByStateDraftOrDeleted() {
		
		Query query = getSession().getNamedQuery("excludeDraftOrDeletedContent");
		query.setParameter("contentProblem", ContentProblem.DRAFT_OR_DELETED);
		query.executeUpdate();
	}

	@Override
	public void clearProblems() {
		
		Query query = getSession().getNamedQuery("clearContentProblems");
		query.executeUpdate();
	}
}