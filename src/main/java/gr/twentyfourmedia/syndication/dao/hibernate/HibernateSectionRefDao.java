package gr.twentyfourmedia.syndication.dao.hibernate;

import gr.twentyfourmedia.syndication.dao.SectionRefDao;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.SectionRef;

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
}
