package gr.twentyfourmedia.syndication.dao.hibernate;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.SectionDao;
import gr.twentyfourmedia.syndication.model.Section;

@Repository
public class HibernateSectionDao extends HibernateAbstractDao<Section> implements SectionDao {

	@Override
	public Section getBySourceId(String sourceId) {

		Query query = getSession().getNamedQuery("findSectionBySourceId");
		query.setParameter("sourceId", sourceId);
		return (Section) query.uniqueResult();
	}
	
	@Override
	public Section getByUniqueNameElement(String uniqueNameElement) {

		Query query = getSession().getNamedQuery("findSectionByUniqueNameElement");
		query.setParameter("uniqueNameElement", uniqueNameElement);
		return (Section) query.uniqueResult();
	}
}
