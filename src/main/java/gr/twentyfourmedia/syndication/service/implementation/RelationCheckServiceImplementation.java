package gr.twentyfourmedia.syndication.service.implementation;

import java.util.List;

import gr.twentyfourmedia.syndication.dao.RelationCheckDao;
import gr.twentyfourmedia.syndication.model.RelationCheck;
import gr.twentyfourmedia.syndication.service.RelationCheckService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RelationCheckServiceImplementation implements RelationCheckService {

	@Autowired
	private RelationCheckDao relationCheckDao;
	
	@Override
	public void persisteRelationCheckEntry(Long contentApplicationId, String contentType, String source, String sourceId, String relationType) {
		
		relationCheckDao.persistEntry(contentApplicationId, contentType, source, sourceId, relationType);
	}
	
	@Override
	public List<RelationCheck> getRelationChecks() {
		
		return relationCheckDao.getEntries();
	}

	@Override
	public void deleteRelationCheckTable() {

		relationCheckDao.deleteTable();
	}
}
