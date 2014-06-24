package gr.twentyfourmedia.syndication.service.implementation;

import java.util.List;

import gr.twentyfourmedia.syndication.dao.AuthorDao;
import gr.twentyfourmedia.syndication.dao.ContentDao;
import gr.twentyfourmedia.syndication.dao.FieldDao;
import gr.twentyfourmedia.syndication.dao.FieldElementDao;
import gr.twentyfourmedia.syndication.dao.SectionRefDao;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.service.ContentService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContentServiceImplementation implements ContentService {

	@Autowired
	private ContentDao contentDao;
	
	@Autowired
	private SectionRefDao sectionRefDao;
	
	@Autowired
	private AuthorDao authorDao;
	
	@Autowired
	private FieldDao fieldDao;
	
	@Autowired
	private FieldElementDao fieldElementDao;
	
	@Override
	public void persistContent(Content content) {

		contentDao.persist(content);
		
		sectionRefDao.persistContentSectionRefs(content);
		authorDao.persistContentAuthors(content);
		fieldDao.persistContentFields(content);
	}
	
	@Override
	public void mergeContent(Content content) {
		
		contentDao.merge(content);
	}

	@Override
	public List<Content> getContents() {
		
		return contentDao.getAll();
	}
}
