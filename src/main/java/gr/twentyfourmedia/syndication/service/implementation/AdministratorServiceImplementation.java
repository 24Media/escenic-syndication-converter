package gr.twentyfourmedia.syndication.service.implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import gr.twentyfourmedia.syndication.model.AnchorInline;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.ContentProblem;
import gr.twentyfourmedia.syndication.model.Relation;
import gr.twentyfourmedia.syndication.model.RelationInline;
import gr.twentyfourmedia.syndication.model.RelationInlineProblem;
import gr.twentyfourmedia.syndication.service.AdministratorService;
import gr.twentyfourmedia.syndication.service.AnchorInlineService;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.RelationInlineService;
import gr.twentyfourmedia.syndication.service.RelationService;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdministratorServiceImplementation implements AdministratorService {

	@Autowired
	private ContentService contentService;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private RelationInlineService relationInlineService;
	
	@Autowired
	private AnchorInlineService anchorInlineService;

	/**
	 * Parse Body Field Of All 'news' Contents and Persist Possible Inline Relations
	 */
	@Override
	public void parseInlineRelations() {
	
		List<Content> contents = contentService.getContentsByType("news", "excludeEverything");
		
		for(Content c : contents) {
			
			String body = contentService.getContentFieldField(c, "body");
			if(body != null) parseBodyPersistRelationsInline(c, body);
		}
	}
	
	/**
	 * Related Contents Of Contents Must Exist and Not Have A ContentProblem
	 */
	@Override
	public void findMissingRelations() {
		
		List<Content> contents = contentService.getContentsWithRelations("excludeContributors");
		
		for(Content c : contents) {
			
			Set<Relation> relations = c.getRelationSet();
			Iterator<Relation> iterator = relations.iterator();
			boolean missingFound = false;
			
			while(iterator.hasNext()) {
				
				Relation relation = iterator.next();
				Content relatedContent = contentService.getContent(relation.getSourceId(), null);
				
				if(relatedContent == null || relatedContent.getContentProblem() != null) {

					relation.setContentProblem(ContentProblem.MISSING_RELATIONS);
					relationService.mergeRelation(relation);
					missingFound = true;
				}
			}
			
			if(missingFound) { //Content Entity Needs Change Too
				
				contentService.updateContentProblem(c, ContentProblem.MISSING_RELATIONS);
			}
		}
	}
	
	/**
	 * Inline Related Contents Of Contents Must Exist and Not Have A ContentProblem, Otherwise The Parent Content Is Considered Rubbish
	 */
	@Override
	public void findMissingInlineRelations() {
		
		List<Content> contents = contentService.getContentsWithRelationsInline("excludeContributors");
		
		for(Content c : contents) {
			
			Set<RelationInline> relationsInline = c.getRelationInlineSet();
			Iterator<RelationInline> iterator = relationsInline.iterator();
			
			while(iterator.hasNext()) {
				
				Content relatedContent = contentService.getContent(iterator.next().getSourceId(), null); 
					
				if(relatedContent == null || relatedContent.getContentProblem() != null) {
					
					contentService.updateContentProblem(c, ContentProblem.MISSING_INLINE_RELATIONS);
					break; //No Reason To Continue, Content With Missing Inline Relations Is Rubbish
				}
			}
		}
	}
	
	/**
	 * If Contents Have Inline Relations Check For Existence Of Duplicates 
	 */
	@Override
	public void findDuplicateInlineRelations() {
		
		List<Content> contents = contentService.getContentsWithRelationsInline("excludeContributors");
		
		for(Content c : contents) {
			
			boolean duplicatesFound = false;
			String previousSourceId = null;
			Iterator<RelationInline> iterator = c.getRelationInlineSet().iterator();
		    
			while(iterator.hasNext()) {
			
				RelationInline relationInline = iterator.next();
				String currentSourceId = relationInline.getSourceId();
				
				if(currentSourceId != null && currentSourceId.equals(previousSourceId)) { //Duplicate
					
					relationInline.setRelationInlineProblem(RelationInlineProblem.RELATIONS_NEEDS_REPLACEMENT);
					relationInlineService.mergeRelationInline(relationInline);
					duplicatesFound = true;
				}
					
				previousSourceId = currentSourceId; //Change Previous Value Before Continuing
			}
			
			if(duplicatesFound) { //Content Entity Needs Change Too

				c.setRelationInlineProblem(RelationInlineProblem.RELATIONS_NEEDS_REPLACEMENT);
				contentService.mergeContent(c, false);
			}
		}
	}
	
	/**
	 * If Contents Have Duplicate Inline Relations Read Inline Anchors From RSS Feed
	 */
	@Override
	public void parseInlineAnchors(int publicationId, String ident) throws IOException {
		
		//Contents With One Of The Following contentProblem Are Excluded From Any Further Processing
		List<ContentProblem> contentProblems = new ArrayList<ContentProblem>();
		contentProblems.add(ContentProblem.MISSING_BINARIES);
		contentProblems.add(ContentProblem.EXCLUDED_BY_SECTION);
		contentProblems.add(ContentProblem.DRAFT_OR_DELETED);
		contentProblems.add(ContentProblem.MISSING_INLINE_RELATIONS);
		
		List<RelationInlineProblem> relationInlineProblems = new ArrayList<RelationInlineProblem>();
		relationInlineProblems.add(RelationInlineProblem.RELATIONS_NEEDS_REPLACEMENT);
		
		List<Content> contents = contentService.getContentsExcludingContentProblemsIncludingRelationInlineProblems(contentProblems, relationInlineProblems, "excludeEverything");
		
		for(Content c : contents) {
			
			parseBodyPersistAnchorsInline(c, publicationId, ident);
		}
	}
	
	/**
	 * If Duplicate RelationsInline That Need Replacement Equals AnchorsInline That Does Not Exist In Body Read By RSS Feed, Content Item Can Be Fixed
	 */
	@Override
	public void characterizeContentAndRelationsInline() {
		
		List<Content> contents = contentService.getContentsWithAnchorsInline(null);
		
		for(Content c : contents) {
			
			int notExistingAnchors = 0;
			int duplicateRelations = 0;
			
			String prologue = contentService.getContentFieldField(c, "prologue");
			String body = contentService.getContentFieldField(c, "body");
			Set<AnchorInline> anchors = c.getAnchorInlineSet();
			Set<RelationInline> relations = c.getRelationInlineSet();
			
			for(AnchorInline a : anchors) { //Count Not Existing Anchors
		
				if((prologue+body).indexOf(a.getAnchor()) == -1) notExistingAnchors++; //RSS Feed Returns prologue + body As Content
			}
			
			for(RelationInline r : relations) { //Count Duplicate Inline Relations
				
				if(r.getRelationInlineProblem()!= null && r.getRelationInlineProblem().equals(RelationInlineProblem.RELATIONS_NEEDS_REPLACEMENT)) duplicateRelations++;
			}
			
			/*
			 * Characterize Content And Inline Relations
			 */
			RelationInlineProblem problem = (notExistingAnchors == duplicateRelations) ? RelationInlineProblem.RELATIONS_CAN_BE_REPLACED : RelationInlineProblem.RELATIONS_CANNOT_BE_REPLACED;
			
			c.setRelationInlineProblem(problem);
			contentService.mergeContent(c, false);

			for(RelationInline r : relations) {
				
				if(r.getRelationInlineProblem()!=null && r.getRelationInlineProblem().equals(RelationInlineProblem.RELATIONS_NEEDS_REPLACEMENT)) {
				
					r.setRelationInlineProblem(problem);
					relationInlineService.mergeRelationInline(r);
				}
			}
		}
	}

	/**
	 * Parse Content's Body Field and Persist Possible Inline Relations
	 * @param content Content
	 * @param body Content's Body Field
	 */
	@Override
	public void parseBodyPersistRelationsInline(Content content, String body) {

		String input = body.replaceAll("<!\\[CDATA\\[", "").replaceAll("\\]\\]>", "");
		Element jsoup = Jsoup.parse(input);
		Elements relations = jsoup.select("relation");
		
		for(Element r : relations) {
					
			RelationInline relationInline = new RelationInline();
			relationInline.setContentApplicationId(content);
			relationInline.setSource(r.attr("source"));
			relationInline.setSourceId(r.attr("sourceid"));
			
			relationInlineService.persistRelationInline(relationInline);
		}
	}

	/**
	 * Parse Content's Body Field As Read From RSS Feed and Persist Possible Inline Anchors
	 * @param content Content
	 * @param publicationId Content's Publication Id
	 * @param ident Ident
	 */
	@Override
	public void parseBodyPersistAnchorsInline(Content content, int publicationId, String ident) throws IOException {

		Long articleId = Long.valueOf(content.getUri().replaceAll("article", "").replaceAll(".ece", ""));
		
		Document document = null;
		
		for(int i = 1; i <= 3; i++) { //Try To Read RSS Feed

			try {
				
				document = Jsoup.connect("http://feeds.24media.gr/feed/article/?publicationId=" + publicationId + "&articleId=" + articleId + "&ident=" + ident).get();
				break; //Break Immediately If Successful
			}
			catch(IOException exception) { //Swallow Exception For The Two First Tries
				
				if(i == 3) {
					
					throw exception;
				}
				else { //Sleep 5 Seconds
					
					try {
			        
						Thread.sleep(5000);
			        } 
					catch (InterruptedException e) {
			        
						Thread.currentThread().interrupt();
			        }
				}
			}
		}
		
		if(document != null) { //Document Read
		
			//Escaping of '<' and '>' Characters In Needed, Otherwise Jsoup Won't Recognize HTML Tags
			Element body = Jsoup.parse(	document
					.select("content")
					.first()
					.text()
					.replaceAll("&lt;", "<")
					.replaceAll("&gt;", ">"));
	
			Elements links = body.getElementsByTag("a");
	
			for(Element link : links) {
			
				AnchorInline anchorInline = new AnchorInline();
				anchorInline.setContentApplicationId(content);
				anchorInline.setAnchor(link.outerHtml());
				
				anchorInlineService.persistAnchorInline(anchorInline);
			}
		}
	}
}