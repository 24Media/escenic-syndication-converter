package gr.twentyfourmedia.syndication.dao.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.FieldDao;
import gr.twentyfourmedia.syndication.dao.FieldElementDao;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Field;
import gr.twentyfourmedia.syndication.model.FieldElement;

@Repository
public class HibernateFieldDao extends HibernateAbstractDao<Field> implements FieldDao {

	@Autowired
	private FieldElementDao fieldElementDao;
	
	@Override
	public void persistContentFields(Content content) {
		
		for(Field f : content.getFieldList()) {
			
			f.setContentApplicationId(content);
			persist(f);
			
			
			for(FieldElement fe : f.getFieldElementList()) {
				
				fe.setFieldApplicationId(f);
				fieldElementDao.persist(fe);
			}
			
		}
	}
}
