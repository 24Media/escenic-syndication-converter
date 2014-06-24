package gr.twentyfourmedia.syndication.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.ContentDao;
import gr.twentyfourmedia.syndication.model.Content;

@Repository
public class HibernateContentDao extends HibernateAbstractDao<Content> implements ContentDao {

}
