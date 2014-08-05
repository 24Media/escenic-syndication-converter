package gr.twentyfourmedia.syndication.service.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.twentyfourmedia.syndication.dao.SectionDao;
import gr.twentyfourmedia.syndication.model.Section;
import gr.twentyfourmedia.syndication.service.SectionService;

@Service
@Transactional
public class SectionServiceImplementation implements SectionService {

	@Autowired
	private SectionDao sectionDao;
	
	@Override
	public void persistSection(Section section) {
		
		sectionDao.persist(section);
	}

	@Override
	public void mergeSection(Section section) {
		
		sectionDao.merge(section);
	}

	@Override
	public Section getSectionByApplicationId(Long applicationId) {
		
		return sectionDao.get(applicationId);
	}	
		
	@Override
	public Section getSectionBySourceId(String sourceId) {
		
		return sectionDao.getBySourceId(sourceId);
	}	
	
	@Override
	public Section getSectionByUniqueNameElement(String uniqueNameElement) {
		
		return sectionDao.getByUniqueNameElement(uniqueNameElement);
	}
	
	@Override
	public List<Section> getSections() {

		return sectionDao.getAll();
	}

	@Override
	public boolean sectionExistsBySourceId(String sourceId) {
		
		if(getSectionBySourceId(sourceId) != null) return true; else return false;
	}	
	
	@Override
	public boolean sectionExistsByUniqueNameElement(String uniqueNameElement) {
		
		if(getSectionByUniqueNameElement(uniqueNameElement) != null) return true; else return false;
	}
	
	@Override
	public void deleteSection(Long applicationId) {
		
		sectionDao.deleteById(applicationId);
	}

	@Override
	public Long countSection() {
		
		return sectionDao.count();
	}
}
