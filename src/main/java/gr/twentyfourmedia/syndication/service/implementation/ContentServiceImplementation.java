package gr.twentyfourmedia.syndication.service.implementation;

import java.util.List;

import gr.twentyfourmedia.syndication.dao.AuthorDao;
import gr.twentyfourmedia.syndication.dao.ContentDao;
import gr.twentyfourmedia.syndication.dao.FieldDao;
import gr.twentyfourmedia.syndication.dao.RelationDao;
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
	private RelationDao relationDao;	

	@Autowired
	private FieldDao fieldDao;

	@Autowired
	private AuthorDao authorDao;
	
	@Override
	public void persistContent(Content content) {

		contentDao.persist(content);
		
		/*
		 * Persist All Content's References
		 */
		sectionRefDao.persistContentSectionRefs(content);
		relationDao.persistContentRelations(content);
		fieldDao.persistContentFields(content);
		authorDao.persistContentAuthors(content);
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
