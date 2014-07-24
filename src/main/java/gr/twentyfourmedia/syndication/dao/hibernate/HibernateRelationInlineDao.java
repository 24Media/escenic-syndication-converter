package gr.twentyfourmedia.syndication.dao.hibernate;

import gr.twentyfourmedia.syndication.dao.RelationInlineDao;
import gr.twentyfourmedia.syndication.model.RelationInline;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRelationInlineDao extends HibernateAbstractDao<RelationInline> implements RelationInlineDao {

	@Override
	public void deleteAll() {

		Query query = getSession().getNamedQuery("deleteAllRelationsInline");
		query.executeUpdate();
	}
}
