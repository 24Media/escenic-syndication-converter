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
	public List<Section> getSections() {

		return sectionDao.getAll();
	}
}
