package gr.twentyfourmedia.syndication.service.implementation;

import java.util.ArrayList;
import java.util.List;

import gr.twentyfourmedia.syndication.dao.AuthorDao;
import gr.twentyfourmedia.syndication.dao.ContentDao;
import gr.twentyfourmedia.syndication.dao.FieldDao;
import gr.twentyfourmedia.syndication.dao.RelationDao;
import gr.twentyfourmedia.syndication.dao.SectionRefDao;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Field;
import gr.twentyfourmedia.syndication.model.SectionRef;
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
	public Content getContent(Long id) {
		
		return contentDao.get(id);
	}
	
	@Override
	public Content getContent(String sourceId) {
		
		return contentDao.getBySourceId(sourceId);
	}
	
	@Override
	public String getContentHomeSection(Content content) {
		
		String result = null;
		
		for(SectionRef r : content.getSectionRefSet()) {
			
			if(r.getHomeSection()!=null && r.getHomeSection().equals("true")) {
				
				result = r.getUniqueName();
			}
		}
		
		return result;
	}
	
	@Override
	public String getPictureContentBinaryName(Content content) {
		
		String result = null;
		
		if(content!=null && content.getType().equals("picture")) {
			
			for(Field f : content.getFieldList()) {
				
				if(f.getName()!=null && f.getName().equals("binary")) {
					
					result = f.getField();
				}
			}
		}
		
		return result;
	}
	
	@Override
	public List<Content> getContents() {
		
		return contentDao.getAll();
	}
	
	@Override
	public List<Content> getContentsByType(String type) {
		
		return contentDao.getByType(type);
	}
	
	@Override
	public List<Content> getContentsByTypeAndHomeSections(String type, List<String> homeSections) {
		
		List<Content> contentList = new ArrayList<Content>();
		List<SectionRef> sectionRefList = sectionRefDao.getByHomeSections(homeSections);
		
		for(SectionRef r : sectionRefList) {
		
			if(r.getContentApplicationId().getType().equals(type)) {
				
				contentList.add(r.getContentApplicationId());
			}
		}
		
		return contentList;
	}
	
	@Override
	public List<Content> getContentsByTypeExcludingHomeSections(String type, List<String> homeSections) {
		
		List<Content> contentList = new ArrayList<Content>();
		List<SectionRef> sectionRefList = sectionRefDao.getExcludingHomeSections(homeSections);
		
		for(SectionRef r : sectionRefList) {
		
			if(r.getContentApplicationId().getType().equals(type)) {
				
				contentList.add(r.getContentApplicationId());
			}
		}
		
		return contentList;
	}
}