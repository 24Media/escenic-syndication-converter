package gr.twentyfourmedia.syndication.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.FieldDao;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Field;

@Repository
public class HibernateFieldDao extends HibernateAbstractDao<Field> implements FieldDao {

	@Override
	public void persistContentFields(Content content) {
		
		for(Field f : content.getFieldList()) {
			
			f.setContentApplicationId(content);
			System.out.println(f.getField());
			persist(f);
		}
	}
}
