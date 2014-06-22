package gr.twentyfourmedia.syndication.dao.hibernate;

import gr.twentyfourmedia.syndication.dao.ParentDao;
import gr.twentyfourmedia.syndication.model.Parent;

import org.springframework.stereotype.Repository;

@Repository
public class HibernateParentDao extends HibernateAbstractDao<Parent> implements ParentDao {

}
