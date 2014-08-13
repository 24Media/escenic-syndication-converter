package gr.twentyfourmedia.syndication.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.twentyfourmedia.syndication.dao.SectionRefDao;
import gr.twentyfourmedia.syndication.model.SectionRef;
import gr.twentyfourmedia.syndication.service.SectionRefService;

@Service
@Transactional
public class SectionRefServiceImplementation implements SectionRefService {

	@Autowired
	private SectionRefDao sectionRefDao;
	
	@Override
	public void persistSectionRef(SectionRef sectionRef) {

		sectionRefDao.persist(sectionRef);
	}

	@Override
	public void mergeSectionRef(SectionRef sectionRef) {

		sectionRefDao.merge(sectionRef);
	}
}
