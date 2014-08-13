package gr.twentyfourmedia.syndication.service.implementation;

import gr.twentyfourmedia.syndication.dao.RelationDao;
import gr.twentyfourmedia.syndication.model.Relation;
import gr.twentyfourmedia.syndication.service.RelationService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RelationServiceImplementation implements RelationService {

	@Autowired
	private RelationDao relationDao;
	
	@Override
	public void persistRelation(Relation relation) {
	
		relationDao.persist(relation);
	}

	@Override
	public void mergeRelation(Relation relation) {

		relationDao.merge(relation);
	}

	@Override
	public void clearRelationProblems() {

		relationDao.clearProblems();
	}
}