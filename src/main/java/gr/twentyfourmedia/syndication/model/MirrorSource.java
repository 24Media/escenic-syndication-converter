package gr.twentyfourmedia.syndication.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
 * <mirror-source
 * 		id-ref="text"?
 * 		(source="text" sourceid="text")?
 * 		dbid="text"?
 * 		exported-dbid="text"?
 * 		publication-name="text"?
 * 		unique-name="text"?
 * 	/>
 * 
 *  Note : publication-name Is Not Referenced In Escenic Documentation
 */
@Entity
@Table(name = "mirrorSource")
@XmlRootElement(name = "mirror-source")
@XmlAccessorType(XmlAccessType.FIELD)
public class MirrorSource {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId")
	@XmlTransient
	private Long applicationId;	
	
	@OneToOne(mappedBy = "mirrorSourceElement")
	@XmlTransient
	private Section section;
	
	/**
	 * The id of the section this section is to mirror. If this attribute is specified, a section element
	 * with an id attribute that matches this attribute must appear somewhere before this mirror-source
	 * element in the syndication file. The referenced section element must have its mirror-source attribute 
	 * set to true. If dbid or source and sourceid are specified, then this attribute is ignored
	 */
	@XmlAttribute(name = "id-ref")
	private String idRef;
	
	/**
	 * The source of the section this section is to mirror. If this attribute is specified, then
	 * sourceid must also be specified. One of the following two conditions must be satisfied:
	 * 		1) The target publication must already contain a mirrorable section with source and
	 *		sourceid attributes that match source and sourceid , or
	 * 		2) A section element with source and sourceid attributes that match source and
	 *		sourceid must appear somewhere before this mirror-source element in the syndication file. 
	 * 		The referenced section element must have its mirror-source attribute set to true.
	 * If dbid is specified, then source and sourceid are ignored.
	 */
	@XmlAttribute(name = "source")
	private String source;
	
	/**
	 * If this attribute is specified, then source must also be specified
	 */
	@XmlAttribute(name = "sourceid")
	private String sourceId;
	
	/**
	 * The dbid of the section this section is to mirror. If this attribute is specified then one of
	 * the following two conditions must be satisfied:
	 * 		1) The target publication must already contain a mirrorable section with a dbid
	 * 		attribute that matches this attribute, or
	 * 		2) A section element with a dbid attribute that matches this attribute must appear somewhere 
	 * 		before this mirror-source element in the syndication file. The referenced section element must 
	 * 		have its mirror-source attribute set to true.
	 */
	@XmlAttribute(name = "dbid")
	private String dbId;
	
	/**
	 * No documentation available
	 */
	@XmlAttribute(name = "exported-dbid")
	private String exportedDbId;

	/**
	 * No documentation available
	 */
	@Column(name = "publicationName")
	@XmlAttribute(name = "publication-name")
	private String publicationName;
	
	/**
	 * The unique-name or name of the section this section is to mirror. If this attribute is
	 * specified, then one of the following conditions must be satisified:
	 * 		1) The target publication must already contain a mirrorable section with a uniquename
	 *		or name attribute that matches this attribute, or
	 * 		2) A section element with a unique-name or name attribute that matches this attribute
	 * 		must appear somewhere before this mirror-source element in the syndication file.
	 * 		The referenced section element must have its mirror-source attribute set to true.
	 * If this is not the case, or if there is a matching name attribute but it is not unique, then
	 * import will fail. If dbid or source and sourceid or id are specified, then this attribute is ignored.
	 */
	@XmlAttribute(name = "unique-name")
	private String uniqueName;

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
	
	public void setSection(Section section) {
		
		this.section = section;
	}
	
	public Section getSection() {
		
		return section;
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
	
	public void setExportedDbId(String exportedDbId) {
		
		this.exportedDbId = exportedDbId;
	}
	
	public String getExportedDbId() {
		
		return exportedDbId;
	}
	
	public void setPublicationName(String publicationName) {
		
		this.publicationName = publicationName;
	}
	
	public String getPublicationName() {
		
		return publicationName;
	}
	
	public void setUniqueName(String uniqueName) {
		
		this.uniqueName = uniqueName;
	}
	
	public String getUniqueName() {
		
		return uniqueName;
	}
	
	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}
}