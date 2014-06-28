package gr.twentyfourmedia.syndication.dao.hibernate;

import java.util.List;

import gr.twentyfourmedia.syndication.dao.SectionRefDao;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.SectionRef;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateSectionRefDao extends HibernateAbstractDao<SectionRef> implements SectionRefDao {

	@Override
	public void persistContentSectionRefs(Content content) {

		for(SectionRef r : content.getSectionRefSet()) {
			
			r.setContentApplicationId(content);
			persist(r);
		}
	}
	
	@Override
	public void mergeContentSectionRefs(Content content) {

		for(SectionRef r : content.getSectionRefSet()) {
			
			r.setContentApplicationId(content);
			merge(r);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SectionRef> getByHomeSections(List<String> homeSections) {
		
		Query query = getSession().getNamedQuery("findSectionRefsByHomeSections");
		query.setParameterList("homeSections", homeSections);
		return (List<SectionRef>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SectionRef> getExcludingHomeSections(List<String> homeSections) {

		Query query = getSession().getNamedQuery("findSectionRefsExcludingHomeSections");
		query.setParameterList("homeSections", homeSections);
		return (List<SectionRef>) query.list();
	}
}
