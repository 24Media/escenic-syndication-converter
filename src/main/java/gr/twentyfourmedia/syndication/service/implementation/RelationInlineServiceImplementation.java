package gr.twentyfourmedia.syndication.service.implementation;

import gr.twentyfourmedia.syndication.dao.RelationInlineDao;
import gr.twentyfourmedia.syndication.model.RelationInline;
import gr.twentyfourmedia.syndication.service.RelationInlineService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RelationInlineServiceImplementation implements RelationInlineService {

	@Autowired
	private RelationInlineDao relationInlineDao;
	
	@Override
	public void persistRelationInline(RelationInline relationInline) {

		relationInlineDao.persist(relationInline);
	}

	@Override
	public void mergeRelationInline(RelationInline relationInline) {

		relationInlineDao.merge(relationInline);
	}

	@Override
	public void deleteAllRelationsInline() {

		relationInlineDao.deleteAll();
	}
}
