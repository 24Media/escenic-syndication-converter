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
	public void persistRelationCheckEntry(Long contentApplicationId, String contentType, String contentHomeSection, String source, String sourceId, String relationType) {
		
		relationCheckDao.persistEntry(contentApplicationId, contentType, contentHomeSection, source, sourceId, relationType);
	}
	
	@Override
	public void updateRelationCheckPictureEntry(String sourceId, String pictureBinaryExists) {
		
		relationCheckDao.updatePictureEntries(sourceId, pictureBinaryExists);
	}
	
	@Override
	public List<RelationCheck> getRelationChecks() {
		
		return relationCheckDao.getEntries();
	}
	
	@Override
	public List<String> getDistinctSourceId() {
		
		return relationCheckDao.getDistinctSourceId(); 
	}

	@Override
	public void deleteRelationCheckTable() {

		relationCheckDao.deleteTable();
	}
}
