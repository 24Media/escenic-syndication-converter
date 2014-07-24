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
 * <parent
 *		id-ref="text"?
 *		(source="text" sourceid="text")?
 *		dbid="text"?
 *		exported-dbid="text"?
 * 		unique-name="text"?
 *		inherit-access-control-list="(true|false)"?
 *	/>
 */
@Entity
@Table(name = "parent")
@XmlRootElement(name = "parent")
@XmlAccessorType(XmlAccessType.FIELD)
public class Parent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId")
	@XmlTransient
	private Long applicationId;	
	
	@OneToOne(mappedBy = "parent")
	@XmlTransient
	private Section section;
	
	/**
	 * The id of the parent section. If this attribute is specified, a section element with an id attribute that matches this attribute 
	 * must appear somewhere before this parent element in the syndication file. The referenced section element must have its 
	 * mirror-source attribute set to true.
	 */
	@XmlAttribute(name = "id-ref")
	private String idRef;
	
	/**
	 * The source of the parent section. If this attribute is specified, then sourceid must also be specified. One of the following two 
	 * conditions must be satisfied:
	 *	1) The target publication must already contain a mirrorable section with source and sourceid attributes that match source and 
	 *	sourceid
	 * 	2) A section element with source and sourceid attributes that match source and sourceid must appear somewhere before this parent 
	 * 	element in the syndication file. The referenced section element must have its mirror-source attribute set to true.
	 */
	@XmlAttribute(name = "source")
	private String source;
	
	/**
	 * If this attribute is specified, then source must also be specified
	 */
	@XmlAttribute(name = "sourceid")
	private String sourceId;
	
	/**
	 * The dbid of the parent section. If this attribute is specified then one of the following two conditions must be satisfied:
	 * 	1) The target publication must already contain a mirrorable section with a dbid attribute that matches this attribute, or
	 * 	2) A section element with a dbid attribute that matches this attribute must appear somewhere before this parent element in the 
	 * 	syndication file. The referenced section element must have its mirror-source attribute set to true.
	 * This attribute is never present in syndication files that have been exported from a database. IDs are always written to 
	 * exported-dbid attributes in exported syndication files.
	 */
	@XmlAttribute(name = "dbid")
	private String dbId;
	
	/**
	 * The dbid of the parent section. This attribute is generated during export, but ignored during import
	 */
	@XmlAttribute(name = "exported-dbid")
	private String exportedDbId;
	
	/**
	 * The unique-name or name of the parent section. If this attribute is specified, then one of the following conditions must be 
	 * satisified:
	 * 	1) The target publication must already contain a mirrorable section with a uniquename or name attribute that matches this 
	 * 	attribute
	 * 	2) A section element with a unique-name or name attribute that matches this attribute must appear somewhere before this parent 
	 * 	element in the syndication file. The referenced section element must have its mirror-source attribute set to true.
	 * If this is not the case, or if there is a matching name attribute but it is not unique, then import will fail.
	 */
	@XmlAttribute(name = "unique-name")
	private String uniqueName;
	
	/**
	 * If set to false then the section will not inherit the access control list of the parent section, and the section will maintain 
	 * its own access control list.
	 */
	@XmlAttribute(name = "inherit-access-control-list")
	private String inheritAccessControlList;
	
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
	
	public void setUniqueName(String uniqueName) {
		
		this.uniqueName = uniqueName;
	}
	
	public String getUniqueName() {
		
		return uniqueName;
	}
	
	public void setInheritAccessControllList(String inheritAccessControllList) {
		
		this.inheritAccessControlList = inheritAccessControllList;
	}
	
	public String getInheritAccessControlList() {
		
		return inheritAccessControlList;
	}
	
	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}	
}