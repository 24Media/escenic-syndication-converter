package gr.twentyfourmedia.syndication.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.CreatorDao;
import gr.twentyfourmedia.syndication.model.Creator;

@Repository
public class HibernateCreatorDao extends HibernateAbstractDao<Creator> implements CreatorDao {

}
