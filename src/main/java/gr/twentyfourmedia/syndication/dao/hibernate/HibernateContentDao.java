package gr.twentyfourmedia.syndication.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.ContentDao;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.ContentProblem;
import gr.twentyfourmedia.syndication.model.RelationInlineProblem;

@Repository
public class HibernateContentDao extends HibernateAbstractDao<Content> implements ContentDao {

	@Override
	public Content get(Long applicationId, String filterName) {
		
		if(filterName != null) getSession().enableFilter(filterName);
		return get(applicationId);
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

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Map<String, Long>> summary(String namedQuery) {
		
		Query query = getSession().getNamedQuery(namedQuery);

		List<Object[]> rows = query.list();
		Map<String, Map<String, Long>> result = new HashMap<String, Map<String, Long>>();
		
        for(Object[] row : rows) {
            
        	String type = (String) row[0];
        	Map<String, Long> counting;
        	if(result.get(type) == null) counting = new HashMap<String, Long>(); else counting = result.get(type);
        	
        	String problem = row[1]==null ? "null" : row[1].toString();
        	Long count = (Long) row[2];
        	counting.put(problem, count);
        	
        	result.put(type, counting);
        }
		
        return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Map<String, Long>> combinedSummary() {

		Query query = getSession().getNamedQuery("problemAndRelationInlineSummary");

		List<Object[]> rows = query.list();
		Map<String, Map<String, Long>> result = new HashMap<String, Map<String, Long>>();
		
        for(Object[] row : rows) {
            
        	String problem = row[0]==null ? "null" : row[0].toString();
        	Map<String, Long> counting;
        	if(result.get(problem) == null) counting = new HashMap<String, Long>(); else counting = result.get(problem);
        	
        	String duplicate = row[1]==null ? "null" : row[1].toString();
        	Long count = (Long) row[2];
        	counting.put(duplicate, count);
        	
        	result.put(problem, counting);
        }
		
        return result;	
	}

	@Override
	public Long countByType(String type) {
		
		Query query = getSession().getNamedQuery("countContentByType");
		query.setParameter("type", type);
		
		return (Long) query.uniqueResult();
	}
}