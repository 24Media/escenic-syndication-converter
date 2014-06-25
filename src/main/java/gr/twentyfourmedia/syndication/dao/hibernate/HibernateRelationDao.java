package gr.twentyfourmedia.syndication.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.RelationDao;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Relation;

@Repository
public class HibernateRelationDao  extends HibernateAbstractDao<Relation> implements RelationDao {

	@Override
	public void persistContentRelations(Content content) {
		
		for(Relation r : content.getRelationSet()) {
			
			r.setContentApplicationId(content);
			persist(r);
		}
	}
}
