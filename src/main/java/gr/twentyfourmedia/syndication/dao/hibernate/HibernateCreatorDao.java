package gr.twentyfourmedia.syndication.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.CreatorDao;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Creator;

@Repository
public class HibernateCreatorDao extends HibernateAbstractDao<Creator> implements CreatorDao {

	@Override
	public void persistContentCreators(Content content) {

		for(Creator c : content.getCreatorSet()) {

			c.setContentApplicationId(content);
			persist(c);
		}
	}
	
	@Override
	public void mergeContentCreators(Content content) {

		for(Creator c : content.getCreatorSet()) {

			c.setContentApplicationId(content);
			merge(c);
		}
	}
}
