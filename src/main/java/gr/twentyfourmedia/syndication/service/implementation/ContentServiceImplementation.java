package gr.twentyfourmedia.syndication.service.implementation;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gr.twentyfourmedia.syndication.dao.AuthorDao;
import gr.twentyfourmedia.syndication.dao.ContentDao;
import gr.twentyfourmedia.syndication.dao.CreatorDao;
import gr.twentyfourmedia.syndication.dao.FieldDao;
import gr.twentyfourmedia.syndication.dao.RelationDao;
import gr.twentyfourmedia.syndication.dao.SectionRefDao;
import gr.twentyfourmedia.syndication.model.AnchorInline;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.ContentProblem;
import gr.twentyfourmedia.syndication.model.Field;
import gr.twentyfourmedia.syndication.model.Relation;
import gr.twentyfourmedia.syndication.model.RelationInline;
import gr.twentyfourmedia.syndication.model.RelationInlineProblem;
import gr.twentyfourmedia.syndication.model.SectionRef;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.RelationInlineService;

import javax.transaction.Transactional;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
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
	
	@Autowired
	private CreatorDao creatorDao;
	
	@Autowired
	private RelationInlineService relationInlineService;
	
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
		if(content.getCreatorSet()!=null && !content.getCreatorSet().isEmpty()) creatorDao.persistContentCreators(content);
	}
	
	@Override
	public void mergeContent(Content content, boolean initialize) {
		
		contentDao.merge(content);
		
		if(initialize == true) {
		
			/*
			 * Merge All Content's References
			 */
			if(content.getSectionRefSet()!=null && !content.getSectionRefSet().isEmpty()) sectionRefDao.mergeContentSectionRefs(content);
			if(content.getRelationSet()!=null && !content.getRelationSet().isEmpty()) relationDao.mergeContentRelations(content);
			if(content.getFieldList()!=null && !content.getFieldList().isEmpty()) fieldDao.mergeContentFields(content);
			if(content.getAuthorSet()!=null && !content.getAuthorSet().isEmpty()) authorDao.mergeContentAuthors(content);
			if(content.getCreatorSet()!=null && !content.getCreatorSet().isEmpty()) creatorDao.mergeContentCreators(content);
		}
	}

	@Override
	public Content getContent(Long applicationId, String filterName) {
		
		return contentDao.get(applicationId, filterName);
	}
	
	@Override
	public Content getContent(String sourceId, String filterName) {
		
		return contentDao.getBySourceId(sourceId, filterName);
	}
	
	@Override
	public Content getRandomContent(String filterName) {
		
		return contentDao.getRandom(filterName);
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
	public Field getContentField(Content content, String fieldName) {
		
		Field body = null;
		
		for(Field f : content.getFieldList()) {
			
			if(f.getName().equals(fieldName)) {
				
				body = f;
				break;
			}
		}
		
		return body;
	}
	
	@Override
	public String getContentFieldField(Content content, String fieldName) {

		Field field = getContentField(content, fieldName); 
		
		if(field != null) {
			
			return field.getField(); 
		}
		else {
		
			return null;
		}
	}
	
	@Override
	public List<Content> getContents(String filterName) {
		
		return contentDao.get(filterName);
	}
	
	@Override
	public List<Content> getContentsByType(String type, String filterName) {
		
		return contentDao.getByType(type, filterName);
	}

	@Override
	public List<Content> getContentsWithRelations(String filterName) {
	
		return contentDao.getWithRelations(filterName);
	}

	@Override
	public List<Content> getContentsWithRelationsInline(String filterName) {
	
		return contentDao.getWithRelationsInline(filterName);
	}
	
	@Override
	public List<Content> getContentsWithAnchorsInline(String filterName) {
	
		return contentDao.getWithAnchorsInline(filterName);
	}	

	@Override
	public List<Content> getContentsByContentProblem(ContentProblem contentProblem, String filterName) {
		
		return contentDao.getByContentProblem(contentProblem, filterName);
	}
	
	@Override
	public List<Content> getContentsByRelationInlineProblem(RelationInlineProblem relationInlineProblem, String filterName) {
		
		return contentDao.getByRelationInlineProblem(relationInlineProblem, filterName);
	}
	
	@Override
	public List<Content> getContentsByTypeContentProblemRelationInlineProblem(String type, ContentProblem contentProblem, RelationInlineProblem relationInlineProblem, String filterName) {
		
		return contentDao.getByTypeContentProblemRelationInlineProblem(type, contentProblem, relationInlineProblem, filterName);
	}
	
	@Override
	public List<Content> getContentsExcludingContentProblemsIncludingRelationInlineProblem(List<ContentProblem> contentProblems, RelationInlineProblem relationInlineProblem, String filterName) {
		
		return contentDao.getExcludingContentProblemsIncludingRelationInlineProblem(contentProblems, relationInlineProblem, filterName);
	}

	/**
	 * If <![CDATA[...]]> Element Created, Set Corresponding Object Attributes To Ensure That Merging Of Content Will Merge These Attributes Too 
	 * @param content Content Object
	 * @param path Path To Syndication File
	 */
	@Override
	public void handleContentHTMLFields(Content content, String path) {
		
		/*
		 * Set HTML Content Fields 
		 */
		if(content.getFieldList()!=null) {
		
			for(Field f : content.getFieldList()) {
			
				if(f.getField()==null) { //Possible HTML Value
			
					String result = getFieldHTMLContent(path, f.getName(), content.getSourceId(), null);
					if(result!=null) f.setField(result);
				}
			}
		}
		
		/*
		 * Set HTML Content Relation Field
		 */
		if(content.getRelationSet()!=null) {
		
			for(Relation r : content.getRelationSet()) {
		
				if(r.getFieldList()!=null) {
		
					for(Field f : r.getFieldList()) {
		
						if(f.getField()==null) { //Possible HTML Value
					
							String result = getFieldHTMLContent(path, f.getName(), content.getSourceId(), r.getSourceId());
							if(result!=null) f.setField(result);
						}
					}
				}
			}
		}
	}	
	
	/**
	 * Given a Content or A Content's Relation, Parse Syndication File and Wrap Found Value With <![CDATA[...]]> Tokens
	 * @param path Path To Syndication File
	 * @param htmlField Field HTML Name
	 * @param contentSourceId Content's Source Id
	 * @param relationSourceId Relation's Source Id
	 * @return Content or Relation's Value Wrapped With <![CDATA[...]]> Tokens
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getFieldHTMLContent(String path, String htmlField, String contentSourceId, String relationSourceId) {
        
		Document doc;
        String result = null;
        
        try {
        	
            File xml = new File(path);
            SAXReader reader = new SAXReader();
            doc = reader.read(xml);
            Element root = doc.getRootElement();
            List<Element> contents = root.elements("content");
            List<Element> fields;
            
            for(Element content : contents) {
            	
            	String contentId = content.attributeValue("sourceid");
            	
                if(contentId!=null && !contentId.isEmpty() && contentId.equals(contentSourceId)) {
                	
                	if(relationSourceId == null) { //Content Fields
                		
	                	fields = content.elements("field");
	                    
	                    for(Element field : fields) {
	                        
	                    	String fieldName = field.attributeValue("name");
	                        
	                        if(fieldName.equals(htmlField)) {
	                        	
	                        	result = field.asXML();
	                            result = result.replaceAll("<field xmlns=\"http://xmlns.escenic.com/2009/import\" name=\"" + htmlField + "\">", "");
	                            result = result.replaceAll("<field xmlns=\"http://xmlns.escenic.com/2009/import\" name=\"" + htmlField + "\"/>", "");
	                            result = result.replaceAll("</field>", "");
	                            result = "<![CDATA[" + result + "]]>";
	                        }
	                    }
                	}
                	else { //Content Relation Fields
                		
                    	List<Element> relations = content.elements("relation");
                    	
                    	for(Element relation : relations) {
                    		
                    		String relationId = relation.attributeValue("sourceid");
                    		
                    		if(relationId!=null && !relationId.isEmpty() && relationId.equals(relationSourceId)) {

    		                	fields = relation.elements("field");
    		                    
    		                    for(Element field : fields) {
    		                        
    		                    	String fieldName = field.attributeValue("name");
    		                        
    		                        if(fieldName.equals(htmlField)) {
    		                        	
    		                        	result = field.asXML();
    		                            result = result.replaceAll("<field xmlns=\"http://xmlns.escenic.com/2009/import\" name=\"" + htmlField + "\">", "");
    		                            result = result.replaceAll("<field xmlns=\"http://xmlns.escenic.com/2009/import\" name=\"" + htmlField + "\"/>", "");
    		                            result = result.replaceAll("</field>", "");
    		                            result = "<![CDATA[" + result + "]]>";
    		                        }
    		                    }
                    		}
                    	}
                	}
                }
            }
        }
        catch(DocumentException exception) {
        
        	System.out.println(exception);
        }
        
        if(result!=null && !result.equals("<![CDATA[]]>")) return result; else return null;
    }

	@Override
	public boolean contentExists(String sourceId) {

		if(getContent(sourceId, "excludeEverything") != null) return true; else return false;
	}

	@Override
	public void excludeContentByTypeAndHomeSections(String type, String description) {
		
		List<SectionRef> sectionRefList = sectionRefDao.getByHomeSections(homeSectionsListFromString(description));
		
		for(SectionRef r : sectionRefList) {
		
			if(r.getContentApplicationId().getType().equals(type)) {
				
				Content c = r.getContentApplicationId();
				c.setContentProblem(ContentProblem.EXCLUDED_BY_SECTION);
				mergeContent(c, false);	
			}
		}		
	}	
	
	@Override
	public void excludeContentByStateDraftOrDeleted() {
	
		contentDao.excludeByStateDraftOrDeleted();
	}
	
	@Override
	public void clearContentProblems() {
		
		contentDao.clearProblems();
	}
	
	/**
	 * Update Content's ContentProblem Value Based On Current Value and Value Given
	 * @param content Content
	 * @param contentProblem Current ContentProblem Value
	 */
	@Override
	public void updateContentProblem(Content content, ContentProblem contentProblem) {
		
		ContentProblem current = content.getContentProblem();

		/*
		 * Order Of Problems Does Matter. MISSING_BINARIES Wins Everyone, DRAFT_OR_DELETED Wins MISSING_INLINE_RELATIONS and MISSING_RELATIONS etc.
		 */
		switch(contentProblem) {
			case MISSING_BINARIES:
				content.setContentProblem(contentProblem);
				mergeContent(content, false);
				break;
			case EXCLUDED_BY_SECTION:
				if(current == null || current.equals(ContentProblem.DRAFT_OR_DELETED) || current.equals(ContentProblem.MISSING_INLINE_RELATIONS) || current.equals(ContentProblem.MISSING_RELATIONS)) {
					content.setContentProblem(contentProblem);
					mergeContent(content, false);
				}
				break;
			case DRAFT_OR_DELETED:
				if(current == null || current.equals(ContentProblem.MISSING_INLINE_RELATIONS) || current.equals(ContentProblem.MISSING_RELATIONS)) {
					content.setContentProblem(contentProblem);
					mergeContent(content, false);
				}
				break;
			case MISSING_INLINE_RELATIONS:
				if(current == null || current.equals(ContentProblem.MISSING_RELATIONS)) {
					content.setContentProblem(contentProblem);
					mergeContent(content, false);
				}
				break;				
			case MISSING_RELATIONS:
				if(current == null) {
					content.setContentProblem(contentProblem);
					mergeContent(content, false);
				}
				break;				
		}
	}

	/**
	 * Given a General Description For Home Sections Get A List of All Actual Home Sections Related To That Description 
	 * @param homeSections General Description
	 * @return List of Home Sections
	 */
	private List<String> homeSectionsListFromString(String description) {

		List<String> result = new ArrayList<String>();
		
		if(description.equals("kairos")) {
			
			result.add("kairos");
			result.add("kairos-eidiseis");
			result.add("kairos-environment");
			result.add("kairos-lifestyle");
			result.add("kairos-taksidi");			
		}
		
		return result;
	}

	@Override
	public void deleteContent(Long applicationId) {
		
		contentDao.deleteById(applicationId);
	}
	
	/**
	 * Replace Duplicate Inline Relations Of A Content That Can Be Corrected With Inline Anchors Read From RSS Feed
	 * @param Content With Duplicates
	 * @return Content Without Duplicates or null
	 */
	@Override
	public Content replaceDuplicateRelationsInlineWithAnchors(Content content) {
		
		String prologue = getContentFieldField(content, "prologue"); //No Need To Escape CDATA For Prologue
		String body = getContentFieldField(content, "body").replaceAll("<!\\[CDATA\\[", "").replaceAll("\\]\\]>", "");
		
		Set<RelationInline> relations = content.getRelationInlineSet();
		Set<AnchorInline> anchors = content.getAnchorInlineSet();

		//Check Before Returning That Replacements Done Equals The Expected Number
		int replacementsDone = 0;
		int replacementsExpected = relationInlineService.countRelationsInlineWithGivenProblem(content, RelationInlineProblem.RELATIONS_CAN_BE_REPLACED);
		
		for(AnchorInline a : anchors) {
	
			if((prologue+body).indexOf(a.getAnchor()) == -1) { //If Anchor Does Not Exist It Can Replace A Duplicate

				/*
				 * Find The First Relation Inline With Problem RELATIONS_CAN_BE_REPLACED
				 */
				RelationInline problem = null;
				
				Iterator<RelationInline> iterator = relations.iterator();
				
				while(iterator.hasNext()) {
					
					RelationInline current = iterator.next();
					
					if(current.getRelationInlineProblem()!=null && current.getRelationInlineProblem().equals(RelationInlineProblem.RELATIONS_CAN_BE_REPLACED)) {
						problem = current;
						iterator.remove(); //Remove It From Set So Next Call Finds The Next Problem
						break;
					}
				}
				
				/*
				 * Every Time A Replacement Is Needed Parse Current Body String To Exclude Already Corrected Duplicates
				 */
				org.jsoup.nodes.Element jsoup = Jsoup.parse(body);
				String relation = jsoup
								 .select("relation[sourceid=" + problem.getSourceId() + "]")
								 .get(1)
								 .outerHtml()
								 .replaceAll("(\\r|\\n)", "")
								 .replaceAll("> <", "><")
								 .replaceAll(" />", "/>");

				String replaced = gr.twentyfourmedia.syndication.utilities.StringUtilities.replaceNthOccurrence(body, relation, a.getAnchor(), 2);

				if(!replaced.equals(body)) {
					
					body = replaced;
					replacementsDone++;
				}
				else {
					
					break; //No Need To Continue
				}
			}
		}
		
		/*
		 * Return Content With Body Replaced Or null If Some Error Occurred 
		 */
		if(replacementsDone == replacementsExpected) {
			
			List<Field> contentFields = new ArrayList<Field>();
			
			for(Field f : content.getFieldList()) {
				
				if(f.getName().equals("body")) f.setField("<![CDATA[" + body + "]]>");
				contentFields.add(f);
			}
			
			content.setFieldList(contentFields);
			return content;
		}
		else {
			
			return null;
		}
	}
	
	@Override
	public Map<String, Map<String, Long>> contentSummary(String namedQuery) {
		
		return contentDao.summary(namedQuery);
	}

	@Override
	public Map<String, Map<String, Long>> contentCombinedSummary() {
		
		return contentDao.combinedSummary();
	}

	@Override
	public Long countContentByType(String type) {
		
		return contentDao.countByType(type);
	}
}