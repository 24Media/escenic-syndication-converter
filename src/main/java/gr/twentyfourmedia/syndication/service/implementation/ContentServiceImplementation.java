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
		if(content.getSectionRefSet()!=null && !content.getSectionRefSet().isEmpty()) sectionRefDao.persistContentSectionRefs(content);
		if(content.getRelationSet()!=null && !content.getRelationSet().isEmpty()) relationDao.persistContentRelations(content);
		if(content.getFieldList()!=null && !content.getFieldList().isEmpty()) fieldDao.persistContentFields(content);
		if(content.getAuthorSet()!=null && !content.getAuthorSet().isEmpty()) authorDao.persistContentAuthors(content);
	}
	
	@Override
	public void mergeContent(Content content) {
		
		contentDao.merge(content);
		
		/*
		 * Merge All Content's References
		 */
		if(content.getSectionRefSet()!=null && !content.getSectionRefSet().isEmpty()) sectionRefDao.mergeContentSectionRefs(content);
		if(content.getRelationSet()!=null && !content.getRelationSet().isEmpty()) relationDao.mergeContentRelations(content);
		if(content.getFieldList()!=null && !content.getFieldList().isEmpty()) fieldDao.mergeContentFields(content);
		if(content.getAuthorSet()!=null && !content.getAuthorSet().isEmpty()) authorDao.mergeContentAuthors(content);
	}

	@Override
	public List<Content> getContents() {
		
		return contentDao.getAll();
	}
}
