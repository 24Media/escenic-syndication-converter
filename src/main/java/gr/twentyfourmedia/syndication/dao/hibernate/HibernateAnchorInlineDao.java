package gr.twentyfourmedia.syndication.dao.hibernate;

import gr.twentyfourmedia.syndication.dao.AnchorInlineDao;
import gr.twentyfourmedia.syndication.model.AnchorInline;

import org.springframework.stereotype.Repository;

@Repository
public class HibernateAnchorInlineDao extends HibernateAbstractDao<AnchorInline> implements AnchorInlineDao {

}
