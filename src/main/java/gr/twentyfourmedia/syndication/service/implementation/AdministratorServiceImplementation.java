package gr.twentyfourmedia.syndication.service.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import gr.twentyfourmedia.syndication.model.AnchorInline;
import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.ContentProblem;
import gr.twentyfourmedia.syndication.model.RelationInline;
import gr.twentyfourmedia.syndication.model.RelationInlineProblem;
import gr.twentyfourmedia.syndication.service.AdministratorService;
import gr.twentyfourmedia.syndication.service.AnchorInlineService;
import gr.twentyfourmedia.syndication.service.ContentService;
import gr.twentyfourmedia.syndication.service.RelationInlineService;

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
			
			String body = contentService.getContentHomeFieldField(c);
			if(body != null) parseBodyPersistRelationsInline(c, body);
		}
	}
	
	@Override
	public void findMissingRelations() {
		
		//TODO Method
		//TODO Filter To Exclude Relations (Not Inline, Normal) That Does Not Exist
		//TODO Exclude DB Column For Relations
	}
	
	/**
	 * Inline Related Contents Of Contents Must Exist and Not Have A ContentProblem, Otherwise The Parent Content Is Considered Rubbish
	 */
	@Override
	public void findMissingInlineRelations() {
		
		List<Content> contents = contentService.getContentsWithRelationsInline("excludeAuthors");
		
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
		
		List<Content> contents = contentService.getContentsWithRelationsInline("excludeAuthors");
		
		for(Content c : contents) {
			
			boolean duplicatesFound = false;
			String previousSourceId = null;
			
			Iterator<RelationInline> iterator = c.getRelationInlineSet().iterator();
		    
			while(iterator.hasNext()) {
			
				RelationInline relationInline = iterator.next();
				String currentSourceId = relationInline.getSourceId();
				
				if(currentSourceId != null && currentSourceId.equals(previousSourceId)) { //Duplicate
					
					relationInline.setRelationInlineProblem(RelationInlineProblem.RELATION_NEEDS_REPLACEMENT);
					relationInlineService.mergeRelationInline(relationInline);
					duplicatesFound = true;
				}
					
				previousSourceId = currentSourceId; //Change Previous Value Before Continuing
			}
			
			if(duplicatesFound) { //Content Entity Needs Change Too
				
				c.setContentProblem(ContentProblem.DUPLICATE_INLINE_RELATIONS);
				contentService.mergeContent(c, false);
			}
		}
	}
	
	/**
	 * If Contents Have Duplicate Inline Relations Read Inline Anchors From RSS Feed
	 */
	@Override
	public void parseInlineAnchors(int publicationId, String ident) {
		
		List<Content> contents = contentService.getContentsByContentProblem(ContentProblem.DUPLICATE_INLINE_RELATIONS, "excludeEverything");
		
		for(Content c : contents) {
			
			parseBodyPersistAnchorsInline(c, publicationId, ident);
		}
	}
	
	/**
	 * Parse Content's Body Field and Persist Possible Inline Relations
	 * @param content Content
	 * @param body Content's Body Field
	 */
	@Override
	public void parseBodyPersistRelationsInline(Content content, String body) {
		
		String input = body;
		String split = "<relation ";
		String source = "source=\"";
		String sourceId = "sourceid=\"";
		
		while(input.indexOf(split) > -1) { //Body Field Has More Relations
			
			input = input.substring(input.indexOf(split)+10);
			
			//Keep Two Temporary Variables Because You Cannot Be Sure Which Comes First
			String temporarySource = input.substring(input.indexOf(source)+8);
			String temporarySourceId = input.substring(input.indexOf(sourceId)+10);
			
			RelationInline relationInline = new RelationInline();
			relationInline.setContentApplicationId(content);
			relationInline.setSource(temporarySource.substring(0, temporarySource.indexOf("\"")));
			relationInline.setSourceId(temporarySourceId.substring(0, temporarySourceId.indexOf("\"")));
			
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
	public void parseBodyPersistAnchorsInline(Content content, int publicationId, String ident) {

		Long articleId = Long.valueOf(content.getUri().replaceAll("article", "").replaceAll(".ece", ""));
		
		Document document;
		
		try {
			
			document = Jsoup.connect("http://feeds.24media.gr/feed/article/?publicationId=" + publicationId + "&articleId=" + articleId + "&ident=" + ident).get();

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
				anchorInline.setHref(link.attr("href"));
				if(!link.attr("target").equals("")) anchorInline.setTarget(link.attr("target"));
				anchorInline.setText(link.text());
				
				anchorInlineService.persistAnchorInline(anchorInline);
			}
		}
		catch(IOException exception) {
			
			exception.printStackTrace();
		}
	}	
	
	/**
	 * Given A Publication and Content's ArticleId, Read Content From RSS Feed
	 * @param publicationId Publication's Id
	 * @param articleId Content's Article Id
	 * @param ident Publications Ident
	 * @return RSS Feed's Content As String
	 */
	@Override
	public String getContentFromRSSFeed(int publicationId, Long articleId, String ident) {
		
		StringBuilder builder = new StringBuilder();
		
		try {
			
			URL url = new URL("http://feeds.24media.gr/feed/article/?publicationId=" + publicationId + "&articleId=" + articleId + "&ident=" + ident);
			URLConnection connection = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String line;
			
			while((line = reader.readLine()) != null) {
				
				builder.append(line.trim());
			}
			
			reader.close();
		} 
		catch (MalformedURLException exception) {

			exception.printStackTrace();
		} 
		catch (IOException exception) {
			
			exception.printStackTrace();
		}
		
		return builder.toString();
	}
}