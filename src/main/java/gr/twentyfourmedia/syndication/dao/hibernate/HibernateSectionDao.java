package gr.twentyfourmedia.syndication.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.twentyfourmedia.syndication.dao.SectionDao;
import gr.twentyfourmedia.syndication.model.Section;

@Repository
public class HibernateSectionDao extends HibernateAbstractDao<Section> implements SectionDao {

}
