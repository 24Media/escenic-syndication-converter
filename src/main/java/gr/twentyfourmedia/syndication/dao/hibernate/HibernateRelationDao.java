package gr.twentyfourmedia.syndication.dao.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.FieldDao;
import gr.twentyfourmedia.syndication.dao.RelationDao;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Field;
import gr.twentyfourmedia.syndication.model.Relation;

@Repository
public class HibernateRelationDao extends HibernateAbstractDao<Relation> implements RelationDao {

	@Autowired
	private FieldDao fieldDao;
	
	@Override
	public void persistContentRelations(Content content) {
		
		for(Relation r : content.getRelationSet()) {
			
			r.setContentApplicationId(content);
			persist(r);
			
			for(Field f : r.getFieldList()) {
				
				f.setRelationApplicationId(r);
				fieldDao.persist(f);
			}
		}
	}
	
	@Override
	public void mergeContentRelations(Content content) {
		
		for(Relation r : content.getRelationSet()) {
			
			r.setContentApplicationId(content);
			merge(r);
			
			for(Field f : r.getFieldList()) {
				
				f.setRelationApplicationId(r);
				fieldDao.merge(f);
			}
		}
	}
}
