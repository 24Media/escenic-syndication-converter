package gr.twentyfourmedia.syndication.model;

import java.sql.Timestamp;

/**
 * Plain Old Java Bean for Administrative Tasks. There Is No Need To Be Known To Hibernate or JAXB
 */
public class RelationCheck {

	private Integer applicationId;
	
	private Integer contentApplicationId;
	
	private String contentType;
	
	private String contentHomeSection;
	
	private String source;
	
	private String sourceId;
	
	private String relationType;
	
	private String relatedContentType;
	
	private Timestamp applicationDateUpdated;

	public void setApplicationId(Integer applicationId) {
		
		this.applicationId = applicationId;
	}
	
	public Integer getApplicationId() {
		
		return applicationId;
	}
	
	public void setContentApplicationId(Integer contentApplicationId) {
		
		this.contentApplicationId = contentApplicationId;
	}
	
	public Integer getContentApplicationId() {
		
		return contentApplicationId;
	}
	
	public void setContentType(String contentType) {
		
		this.contentType = contentType;
	}
	
	public String getContentType() {
		
		return contentType;
	}
	
	public void setContentHomeSection(String contentHomeSection) {
		
		this.contentHomeSection = contentHomeSection;
	}
	
	public String getContentHomeSection() {
		
		return contentHomeSection;
	}

	public void setSource(String source) {
		
		this.source = source;
	}
	
	public String getSource() {
		
		return source;
	}
	
	public void setSourceId(String sourceId) {
		
		this.sourceId = sourceId;
	}
	
	public String getSourceId() {
		
		return sourceId;
	}
	
	public void setRelationType(String relationType) {
		
		this.relationType = relationType;
	}
	
	public String getRelationType() {
		
		return relationType;
	}
	
	public void setRelatedContentType(String relatedContentType) {
		
		this.relatedContentType = relatedContentType;
	}
	
	public String getRelatedContentType() {
		
		return relatedContentType;
	}
	
	public void setApplicationDateUpdated(Timestamp applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Timestamp getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}
}
