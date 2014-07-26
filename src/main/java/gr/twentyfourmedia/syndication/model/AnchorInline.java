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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

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
	@Column(name = "href")
	private String href;
	
	@Column(name = "target")
	private String target;
	
	@Lob
	@Column(name = "text")
	private String text;
	
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
	
	public void setHref(String href) {
		
		this.href = href;
	}
	
	public String getHref() {
		
		return href;
	}
	
	public void setTarget(String target) {
		
		this.target = target;
	}
	
	public String getTarget() {
		
		return target;
	}
	
	public void setText(String text) {
		
		this.text = text;
	}
	
	public String getText() {
		
		return text;
	}
	
	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}
}
