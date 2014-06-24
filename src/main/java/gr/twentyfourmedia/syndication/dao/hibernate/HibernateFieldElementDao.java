package gr.twentyfourmedia.syndication.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.FieldElementDao;
import gr.twentyfourmedia.syndication.model.FieldElement;

@Repository
public class HibernateFieldElementDao extends HibernateAbstractDao<FieldElement> implements FieldElementDao {

}
