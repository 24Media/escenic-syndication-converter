package gr.twentyfourmedia.syndication.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.MirrorSourceDao;
import gr.twentyfourmedia.syndication.model.MirrorSource;

@Repository
public class HibernateMirrorSourceDao extends HibernateAbstractDao<MirrorSource> implements MirrorSourceDao  {

}
