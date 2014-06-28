package gr.twentyfourmedia.syndication.service.implementation;

import java.util.List;

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
	public List<Relation> getRelations() {

		return relationDao.getAll();
	}
}
