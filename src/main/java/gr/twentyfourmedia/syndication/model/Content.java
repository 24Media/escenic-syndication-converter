package gr.twentyfourmedia.syndication.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
 * <content
 * 		id="text"?
 * 		(source="text" sourceid="text")?
 * 		dbid="text"?
 * 		exported-dbid="text"?
 * 		state="(draft|submitted|approved|published|deleted)"?
 * 		type="text"?
 * 		publishdate="text"?
 * 		delete-relations="text"?
 * 		activatedate="text"?
 * 		expiredate="text"?
 * 		creationdate="text"?
 * 	>
 * 		<section-ref/>*
 * 		<relation>...</relation>*
 * 		<reference/>*
 * 		<field>...</field>*
 * 		<update/>?
 * 		<author/>*
 * 		<creator/>?
 * 		<priority/>?
 * 	</content>
 * 
 *  Note : Use of <reference /> element is deprecated. It is only retained for reasons of backwards compatibility.
 */
@Entity
@Table(name = "content")
@XmlRootElement(name = "content")
@XmlAccessorType(XmlAccessType.FIELD)
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId")
	@XmlTransient
	private Long applicationId;
	
	/**
	 * A unique identifier for this content element. It is only valid and unique within the
	 * current syndication file and can be used to enable the establishment of relationships
 	 * between elements in the file. Other elements in the file have id-ref attributes that can be
	 * used to reference content elements. If a content element does not have an id attribute
 	 * then it must have either a dbid attribute or both a source and a sourceid attribute. A
	 * content element may have several or all of these attributes, in which case any of them
	 * can be used for establishing relationships. The id attribute is not imported along with content 
	 * items. Unless a dbid has been specified, all content items are assigned new internal IDs during import.
	 */
	@Column(name = "id")
	@XmlAttribute(name = "id")
	private String id;
	
	/**
	 * The name of the system from which this content item originates. Together with the
	 * sourceid attribute it forms a globally unique external identifier for the content item
	 * that can be used for establishing relationships between elements in the syndication file.
	 * Other elements in the file have source and sourceid attributes that can be used for this
	 * purpose. If this attribute is specified then a sourceid attribute must also be specified.
	 * If a content element does not have a source and sourceid attribute then it must have
	 */
	@Column(name = "source")
	@XmlAttribute(name = "source")
	private String source;
	
	/**
	 * Together with the sourceid attribute it forms a globally unique external identifier for the content item
	 */
	@Column(name = "sourceId")
	@XmlAttribute(name = "sourceid")
	private String sourceId;
	
	/**
	 * The internal Content Engine ID of this content item, which can be used when importing updated versions of 
	 * existing content items. It can also be used for establishing relationships between elements in the syndication 
	 * file. Other elements in the file have dbid attributes that can be used for this purpose. If a content element 
	 * does not have a dbid attribute then it must have either a source and sourceid attribute or an id attribute. A content 
	 * element may have several or all of these attributes, in which case any of them can be used for establishing 
	 * relationships. You should only use the dbid attribute when importing updated versions of existing content items.
	 * This attribute is never present in syndication files that have been exported from a database. The ID is always 
	 * written to the exported-dbid attribute in exported syndication files.
	 */
	@Column(name = "dbId")
	@XmlAttribute(name = "dbid")
	private String dbId;
	
	/**
	 * The internal Content Engine ID of this content item, which can be used to identify the content item in the database 
	 * from which it was exported. This attribute is generated during export, but ignored during import.
	 */
	@Column(name = "exportedDbId")
	@XmlAttribute(name = "exported-dbid")
	private String exportedDbId;
	
	/**
	 * The current state of this content item
	 */
	@Column(name = "state")
	@XmlAttribute(name = "state")
	private String state;
	
	/**
	 * Defines the type of content item represented by this content element. For import, the value specified must be 
	 * the name of a content type as defined in the target publication's content-type resource. The value you specify 
	 * here will then determine what kind of field elements the content element may own
	 */
	@Column(name = "type")
	@XmlAttribute(name = "type")
	private String type;
	
	/**
	 * The date this content item was published
	 */
	@Column(name = "publishDate")
	@XmlAttribute(name = "publishdate")
	private String publishDate;
	
	/**
	 * If this attribute is set to true when re-importing an existing content 
	 * item, then all the content item's existing relations are deleted.
	 */
	@Column(name = "deleteRelations")
	@XmlAttribute(name = "delete-relations")
	private String deleteRelations;
	
	/**
	 * The date this content item was/is to be activated
	 */
	@Column(name = "activateDate")
	@XmlAttribute(name = "activatedate")
	private String activateDate;
	
	/**
	 * The date this content item expired/is to expire
	 */
	@Column(name = "expireDate")
	@XmlAttribute(name = "expiredate")
	private String expireDate;
	
	/**
	 * The date this content item was created. If specified, this attribute is used when importing
	 * new content items that do not already exist in the database. It is, however, ignored when
	 * importing updates to content items that already exist. If it is omitted when importing a
	 * new content item, then the new content item's creation date is set to the current date
	 */
	@Column(name = "creationDate")
	@XmlAttribute(name = "creationdate")
	private String creationDate;
	
	@OneToMany(mappedBy = "contentApplicationId", cascade = CascadeType.ALL) /*TODO : Check Where The Owning Side Should Be*/
	@XmlElement(name = "section-ref")
	private List<SectionRef> sectionRefList;
	
	@OneToMany(mappedBy = "contentApplicationId", cascade = CascadeType.ALL) /*TODO : Check Where The Owning Side Should Be*/
	@XmlElement(name = "relation")
	private List<Relation> relationList;
	
	/*
	reference *
	field *
	*/
	
	/**
	 * When importing a content item that already exists in the target publication, you can use this
	 * element to update the content item's source and source ID references
	 */
	@Embedded
	@AttributeOverrides({
        @AttributeOverride(name = "newSource", column = @Column(name = "newSource")),
        @AttributeOverride(name = "newSourceId", column = @Column(name = "newSourceId")),
    })
	@XmlElement(name = "update")
	private Update update;
	
	@OneToMany(mappedBy = "contentApplicationId", cascade = CascadeType.ALL) /*TODO : Check Where The Owning Side Should Be*/
	@XmlElement(name = "author")
	private List<Author> authorList;

	/**
	 * A reference to the creator of a content item. Content item authors are themselves represented by person objects. 
	 * A person object is a special type of content item containing the fields needed to hold the usual kinds of personal 
	 * details (name, phone number, email address and so on). A creator element must therefore contain a reference to a 
	 * person object in the publication or a person element in the syndication file.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "creator", referencedColumnName = "applicationId", nullable = true)
	@XmlElement(name = "creator")	
	private Creator creator;	
	
	/**
	 * Used to set section priority
	 */
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "priority"))
	@XmlElement(name = "priority")
	private Priority priority;
	
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
	
	public void setId(String id) {
		
		this.id = id;
	}

	public String getId() {
		
		return id;
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
	
	public void setState(String state) {
		
		this.state = state;
	}
	
	public String getState() {
		
		return state;
	}
	
	public void setType(String type) {
		
		this.type = type;
	}
	
	public String getType() {
		
		return type;
	}
	
	public void setPublishDate(String publishDate) {
		
		this.publishDate = publishDate;
	}
	
	public String getPublishDate() {
		
		return publishDate;
	}
	
	public void setDeleteRelations(String deleteRelations) {
		
		this.deleteRelations = deleteRelations;
	}
	
	public String getDeleteRelations() {
		
		return deleteRelations;
	}
	
	public void setActivateDate(String activateDate) {
		
		this.activateDate = activateDate;
	}
	
	public String getActivateDate() {
		
		return activateDate;
	}
	
	public void setExpireDate(String expireDate) {
		
		this.expireDate = expireDate;
	}
	
	public String getExpireDate() {
		
		return expireDate;
	}
	
	public void setCreationDate(String creationDate) {
		
		this.creationDate = creationDate;
	}
	
	public String getCreationDate() {
		
		return creationDate;
	}
	
	public void setSectionRefList(List<SectionRef> sectionRefList) {
		
		this.sectionRefList = sectionRefList;
	}
	
	public List<SectionRef> getSectionRefList() {
		
		return sectionRefList;
	}
	
	public void setRelationList(List<Relation> relationList) {
		
		this.relationList = relationList;
	}
	
	public List<Relation> getRelationList() {
		
		return relationList;
	}
	
	
	public void setUpdate(Update update) {
		
		this.update = update;
	}
	
	public Update getUpdate() {
		
		return update;
	}
	
	public void setAuthorList(List<Author> authorList) {
		
		this.authorList = authorList;
	}
	
	public List<Author> getAuthorList() {
		
		return authorList;
	}
	
	public void setCreator(Creator creator) {
		
		this.creator = creator;
	}
	
	public Creator getCreator() {
		
		return creator;
	}
	
	public void setPriority(Priority priority) {
		
		this.priority = priority;
	}
	
	public Priority getPriority() {
		
		return priority;
	}
	
	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}
}
