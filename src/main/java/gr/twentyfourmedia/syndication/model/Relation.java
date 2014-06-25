package gr.twentyfourmedia.syndication.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <relation
 * 		id-ref="text"?
 * 		(source="text" sourceid="text")?
 * 		dbid="text"?
 * 		exported-dbid="text"?
 * 		type="text"
 * >
 * 		<field>...</field>*
 * </relation>
 */
@Entity
@Table(name = "relation")
@XmlRootElement(name = "relation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Relation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId")
	@XmlTransient
	private Long applicationId;

	@ManyToOne
	@JoinColumn(name = "contentApplicationId", referencedColumnName = "applicationId")
	@XmlTransient
	private Content contentApplicationId;
	
	/**
	 * The id of the related content item. If this attribute is specified, a content element with an id 
	 * attribute that matches this attribute must appear somewhere before this relation element in the 
	 * syndication file. If dbid or source and sourceid are specified, then this attribute is ignored.
	 */
	@Column(name = "idRef")
	@XmlAttribute(name = "id-ref")
	private String idRef;
	
	/**
	 * The source of the related content item. If this attribute is specified, then sourceid must
	 * also be specified. One of the following two conditions must be satisfied:
	 * 		1) The target publication must already contain a content item with source and
 	 * 		sourceid attributes that match this element's source and sourceid, or
	 * 		2) A content element with source and sourceid attributes that match source and sourceid 
	 * 		must appear somewhere before this relation element in the syndication file.
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
	 * The dbid of the related content item. If this attribute is specified, then one of the
	 * following two conditions must be satisfied:
	 * 		1) The target publication must already contain a content item with a dbid that matches this attribute, or
	 * 		2) A content element with a dbid attribute that matches this attribute must appear somewhere before 
	 * 		this relation element in the syndication file.
	 * This attribute is never present in syndication files that have been exported from a database. IDs are always 
	 * written to exported-dbid attributes in exported syndication files.
	 */
	@Column(name = "dbId")
	@XmlAttribute(name = "dbid")
	private String dbId;
	
	/**
	 * The dbid of the related content item. This attribute is generated during export, but ignored during import
	 */
	@Column(name = "exportedDbId")
	@XmlAttribute(name = "exported-dbid")
	private String exportedDbId;
	
	/**
	 * Defines the type of relation represented by this relation element. For import, the
	 * value specified must be the name of a relation type as defined in the target publication's
	 * content-type resource. The value you specify here will determine how the relationship
	 * defined by this element is presented both in the publication and in Content Studio.
	 */
	@Column(name = "type")
	@XmlAttribute(name = "type")
	private String type;
	
	@OneToMany(mappedBy = "relationApplicationId", cascade = CascadeType.ALL) /*TODO : Check Where The Owning Side Should Be*/
	@XmlElement(name = "field")
	private List<Field> fieldList;
	
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
	
	public void setExportedDbId(String exportedDbId) {
		
		this.exportedDbId = exportedDbId;
	}
	
	public String getExportedDbId() {
		
		return exportedDbId;
	}
	
	public void setType(String type) {
		
		this.type = type;
	}
	
	public String getType() {
		
		return type;
	}
	
	public void setFieldList(List<Field> fieldList) {
		
		this.fieldList = fieldList;
	}
	
	public List<Field> getFieldList() {
		
		return fieldList;
	}
	
	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}
}
