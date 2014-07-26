package gr.twentyfourmedia.syndication.service.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

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
	}
	
	@Override
	public void findMissingInlineRelations() {
		
		//TODO Method
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
			
			Long articleId = Long.valueOf(c.getUri().replaceAll("article", "").replaceAll(".ece", ""));
			String content = getContentFromRSSFeed(publicationId, articleId, ident);
			
			if(content != null) parseBodyPersistAnchorsInline(c, content);
		}
	}
	
	/**
	 * Parse Content's Body Field and Persist Possible Inline Relations
	 * @param content Content
	 * @param body Content's Body Field
	 */
	@Override
	public void parseBodyPersistRelationsInline(Content content, String body) {
		
		//TODO Replace With A jsoup Alternative
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
	 * Parse Content's Body Field And Persist Possible Inline Anchors
	 * @param contentEntity Content
	 * @param contentString Content As Read From RSS Feed
	 */
	@Override
	public void parseBodyPersistAnchorsInline(Content contentEntity, String contentString) {

		Document document = Jsoup.parse(contentString.replaceAll("&lt;", "<").replaceAll("&gt;", ">")); //jsoup Won't Recognize HTML Anchors If Characters Don't Get Replaced
				
		Elements links = document.getElementsByTag("a");
		
		for(Element link : links) {
			
			AnchorInline anchorInline = new AnchorInline();
			anchorInline.setContentApplicationId(contentEntity);
			anchorInline.setHref(link.attr("href"));
			anchorInline.setTarget(link.attr("target")); //TODO Null In Database
			anchorInline.setText(link.text());
			
			anchorInlineService.persistAnchorInline(anchorInline);
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