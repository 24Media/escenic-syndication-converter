package gr.twentyfourmedia.syndication.service.implementation;

import gr.twentyfourmedia.syndication.dao.RelationCheckDao;
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
	public void persisteRelationCheckEntry(Long contentApplicationId, String source, String sourceId, String type) {
		
		relationCheckDao.persistEntry(contentApplicationId, source, sourceId, type);
	}

	@Override
	public void deleteRelationCheckTable() {

		relationCheckDao.deleteTable();
	}
}
