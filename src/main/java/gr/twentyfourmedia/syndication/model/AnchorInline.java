package gr.twentyfourmedia.syndication.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@NamedQueries({
	@NamedQuery(
			name = "deleteAllAnchorsInline",
			query = "DELETE FROM AnchorInline"),
})
@Entity
@Table(name = "anchorInline")
public class AnchorInline {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId")
	private Long applicationId;
	
	@ManyToOne
	@JoinColumn(name = "contentApplicationId", referencedColumnName = "applicationId")
	private Content contentApplicationId;
	
	@Lob
	@Column(name = "anchor")
	private String anchor;
		
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "applicationDateUpdated")
	private Calendar applicationDateUpdated;
	
	public void setApplicationId(Long applicationId) {
		
		this.applicationId = applicationId;
	}
	
	public Long getApplicationId() {
		
		return applicationId;
	}
	
	public void setContentApplicationId(Content contentApplicationId) {
		
		this.contentApplicationId = contentApplicationId;
	}
	
	public Content getContentApplicationId() {
		
		return contentApplicationId;
	}
	
	public void setAnchor(String anchor) {
		
		this.anchor = anchor;
	}
	
	public String getAnchor() {
		
		return anchor;
	}

	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}
}
