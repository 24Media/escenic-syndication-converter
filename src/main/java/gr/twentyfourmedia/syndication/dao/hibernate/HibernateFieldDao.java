package gr.twentyfourmedia.syndication.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
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
			persist(f);
		}
	}
	
	@Override
	public void mergeContentFields(Content content) {
		
		for(Field f : content.getFieldList()) {
			
			f.setContentApplicationId(content);
			merge(f);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Field> getByBodyContaining(String token) {
		
		Query query = getSession().getNamedQuery("findFieldBodyContaining");
		query.setParameter("token", '%' + token + '%');
		return (List<Field>) query.list();
	}
}
