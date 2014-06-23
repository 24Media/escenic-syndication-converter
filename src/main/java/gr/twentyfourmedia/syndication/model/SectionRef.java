package gr.twentyfourmedia.syndication.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <section-ref
 * 		id-ref="text"?
 * 		(source="text" sourceid="text")?
 * 		dbid="text"?
 * 		unique-name="text"?
 * 		todesk="(true|false)"?
 * 		home-section="(true|false)"?
 * />
 */
@Entity
@Table(name = "sectionRef")
@XmlRootElement(name = "section-ref")
@XmlAccessorType(XmlAccessType.FIELD)
public class SectionRef {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId")
	@XmlTransient
	private Long applicationId;
	
	@ManyToOne
	@JoinColumn(name = "contentApplicationId", referencedColumnName = "applicationId", nullable = false)
	@XmlTransient
	private Content contentApplicationId;
	
	/**
	 * The id of the section to which the content item represented by the owning content
	 * element belongs/is to be added. If this attribute is specified, a section element with an
	 * id attribute that matches this attribute must appear somewhere before this section-ref element 
	 * in the syndication file. If dbid or source and sourceid are specified, then this attribute is ignored.
	 */
	@Column(name = "idRef")
	@XmlAttribute(name = "id-ref")
	private String idRef;
	
	/**
	 * The source of the section to which the content item represented by the owning content
	 * element belongs/is to be added. If this attribute is specified, then sourceid must also be
	 * specified. One of the following two conditions must be satisfied:
	 * 		1) The target publication must already contain a section with source and sourceid
	 *		attributes that match source and sourceid, or
	 * 		2) A section element with source and sourceid attributes that match source
	 * 		and sourceid must appear somewhere before this section-ref element in the syndication file.
	 * If dbid is specified, then source and sourceid are ignored.
	 */
	@Column(name = "source")
	@XmlAttribute(name = "source")
	private String source;
	
	/**
	 * If this attribute is specified, then source must also be specified
	 */
	@Column(name = "sourceId")
	@XmlAttribute(name = "sourceid")
	private String sourceId;
	
	/**
	 * The dbid of the section to which the content item represented by the owning contentelement belongs/is 
	 * to be added. If this attribute is specified then one of the following two conditions must be satisfied:
	 * 		1) The target publication must already contain a section with a dbid that matches this attribute, or
	 * 		2) A section element with a dbid attribute that matches this attribute must appear somewhere before 
	 * 		this section-ref element in the syndication file.
	 */
	@Column(name = "dbId")
	@XmlAttribute(name = "dbid")
	private String dbId;
	
	/**
	 * The unique-name or name of the section to which the content item represented by the
	 * owning content element belongs/is to be added. If this attribute is specified, then one of
	 * the following conditions must be satisified:
	 * 		1) The target publication must already contain a section with a uniquename or name
	 * 		attribute that matches this attribute, or
	 * 		2) A section element with a unique-name or name attribute that matches this attribute
	 *		must appear somewhere before this section-ref element in the syndication file.
	 * If this is not the case, or if there is a matching name attribute but it is not unique, then import will fail.
	 * If dbid or source and sourceid or id are specified, then this attribute is ignored.
	 */
	@Column(name = "uniqueName")
	@XmlAttribute(name = "unique-name")
	private String uniqueName;
	
	/**
	 * If set to true, then content item represented by the owning content element is added to this section's default inbox (INBOX)
	 */
	@Column(name = "toDesk")
	@XmlAttribute(name = "todesk")
	private String toDesk;
	
	/**
	 * If set to true then this section is the home section of the content item represented by the owning content element
	 */
	@Column(name = "homeSection")
	@XmlAttribute(name = "home-section")
	private String homeSection;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "applicationDateUpdated")
	@XmlTransient
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
	
	public void setIdRef(String idRef) {
		
		this.idRef = idRef;
	}
	
	public String getIdRef() {
		
		return idRef;
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
	
	public void setDbId(String dbId) {
		
		this.dbId = dbId;
	}
	
	public String getDbId() {
		
		return dbId;
	}
	
	public void setUniqueName(String uniqueName) {
		
		this.uniqueName = uniqueName;
	}
	
	public String getUniqueName() {
		
		return uniqueName;
	}
	
	public void setToDesk(String toDesk) {
		
		this.toDesk = toDesk;
	}
	
	public String getToDesk() {
		
		return toDesk;
	}
	
	public void setHomeSection(String homeSection) {
		
		this.homeSection = homeSection;
	}
	
	public String getHomeSection() {
		
		return homeSection;
	}
	
	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}
}
