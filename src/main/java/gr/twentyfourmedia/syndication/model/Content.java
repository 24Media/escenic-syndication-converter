package gr.twentyfourmedia.syndication.model;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
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
 * 		<creator/>*
 * 		<priority/>?
 * 		<uri/>?
 * 	</content>
 * 
 *  Note : Use of <reference /> element is deprecated. It is only retained for reasons of backwards compatibility.
 */
@NamedQueries({
	@NamedQuery(
			name = "findContentBySourceId",
			query = "FROM Content WHERE sourceId = :sourceId"),
	@NamedQuery(
			name = "findContentsByType",
			query = "FROM Content WHERE type = :type"),
	@NamedQuery(
			name = "findContentsWithRelations",
			query = "FROM Content c WHERE c.relationSet IS NOT EMPTY"),
	@NamedQuery(
			name = "findContentsWithRelationsInline",
			query = "FROM Content c WHERE c.relationInlineSet IS NOT EMPTY"),
	@NamedQuery(
			name = "findContentsWithAnchorsInline",
			query = "FROM Content c WHERE c.anchorInlineSet IS NOT EMPTY"),
	@NamedQuery(
			name = "findContentsByContentProblem",
			query = "FROM Content WHERE contentProblem = :contentProblem"),
	@NamedQuery(
			name = "findContentsByRelationInlineProblem",
			query = "FROM Content WHERE relationInlineProblem = :relationInlineProblem"),
	@NamedQuery(
			name = "findContentsExcludingContentProblemsIncludingRelationInlineProblems",
			query = "FROM Content WHERE contentProblem NOT IN (:contentProblem) AND relationInlineProblem IN (:relationInlineProblem)"),
	@NamedQuery(
			name = "countContentByType",
			query = "SELECT COUNT(*) FROM Content c WHERE c.type = :type"),
	@NamedQuery(
			name = "clearContentProblems",
			query = "UPDATE Content c SET c.contentProblem = null"),
	@NamedQuery(
			name = "clearContentDuplicates",
			query = "UPDATE Content c SET c.relationInlineProblem = null"),			
	@NamedQuery(
			name = "excludeContentByStates",
			query = "UPDATE Content c SET c.contentProblem = :contentProblem WHERE c.state IN (:states)"),
	@NamedQuery(
			name = "randomContent",
			query = "FROM Content WHERE contentProblem IS NULL AND relationInlineProblem IS NULL ORDER BY RAND()"),
	@NamedQuery(
			name = "problemSummary",
			query = "SELECT c.type, c.contentProblem, COUNT(*) FROM Content c GROUP BY c.type, c.contentProblem"),
	@NamedQuery(
			name = "relationInlineSummary",
			query = "SELECT c.type, c.relationInlineProblem, COUNT(*) FROM Content c GROUP BY c.type, c.relationInlineProblem"),
	@NamedQuery(
			name = "problemAndRelationInlineSummary",
			query = "SELECT c.contentProblem, c.relationInlineProblem, COUNT(*) FROM Content c WHERE c.type = 'news' GROUP BY c.contentProblem, c.relationInlineProblem")			
})
@FilterDefs({
    @FilterDef(name = "excludeContributors"),
    @FilterDef(name = "excludeAdministrativeEntities"),
    @FilterDef(name = "excludeEverything")
})
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
	 * A unique identifier for this content element. It is only valid and unique within the current syndication file and can be used to 
	 * enable the establishment of relationships between elements in the file. Other elements in the file have id-ref attributes that 
	 * can be used to reference content elements. If a content element does not have an id attribute then it must have either a dbid 
	 * attribute or both a source and a sourceid attribute. A content element may have several or all of these attributes, in which case 
	 * any of them can be used for establishing relationships. The id attribute is not imported along with content items. Unless a dbid 
	 * has been specified, all content items are assigned new internal IDs during import.
	 */
	@Column(name = "id")
	@XmlAttribute(name = "id")
	private String id;
	
	/**
	 * The name of the system from which this content item originates. Together with the sourceid attribute it forms a globally unique 
	 * external identifier for the content item that can be used for establishing relationships between elements in the syndication file.
	 * Other elements in the file have source and sourceid attributes that can be used for this purpose. If this attribute is specified 
	 * then a sourceid attribute must also be specified. If a content element does not have a source and sourceid attribute then it must 
	 * have either a dbid attribute or an id attribute. A content element may have several or all of these attributes, in which case any 
	 * of them can be used for establishing relationships. If supplied, source and sourceid are imported and stored with content items. 
	 * If source and sourceid are supplied and dbid is not supplied, then they are used to lookup an existing content item. If a content 
	 * item with matching source and sourceid is found, then this content item is updated; otherwise a new content item is created.
	 * If supplied, source and sourceid are imported when creating new content items, but not when updating existing content items.
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
	 * The internal Content Engine ID of this content item, which can be used when importing updated versions of existing content items. 
	 * It can also be used for establishing relationships between elements in the syndication file. Other elements in the file have dbid 
	 * attributes that can be used for this purpose. If a content element does not have a dbid attribute then it must have either a 
	 * source and sourceid attribute or an id attribute. A content element may have several or all of these attributes, in which case any
	 * of them can be used for establishing relationships. You should only use the dbid attribute when importing updated versions of 
	 * existing content items. This attribute is never present in syndication files that have been exported from a database. The ID is 
	 * always written to the exported-dbid attribute in exported syndication files.
	 */
	@Column(name = "dbId")
	@XmlAttribute(name = "dbid")
	private String dbId;
	
	/**
	 * The internal Content Engine ID of this content item, which can be used to identify the content item in the database from which it 
	 * was exported. This attribute is generated during export, but ignored during import.
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
	 * Defines the type of content item represented by this content element. For import, the value specified must be the name of a content 
	 * type as defined in the target publication's content-type resource. The value you specify here will then determine what kind of 
	 * field elements the content element may own
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
	 * If this attribute is true when re-importing an existing content item, then all the content item's existing relations are deleted
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
	 * The date this content item was created. If specified, this attribute is used when importing new content items that do not already 
	 * exist in the database. It is, however, ignored when importing updates to content items that already exist. If it is omitted when 
	 * importing a new content item, then the new content item's creation date is set to the current date
	 */
	@Column(name = "creationDate")
	@XmlAttribute(name = "creationdate")
	private String creationDate;
	
	@OneToMany(mappedBy = "contentApplicationId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.SELECT) 
	@OrderBy(value = "applicationId ASC")
	@XmlElement(name = "section-ref")
	private Set<SectionRef> sectionRefSet;
	
	@OneToMany(mappedBy = "contentApplicationId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.SELECT)
	@OrderBy(value = "applicationId ASC")
	@XmlElement(name = "relation")
	private Set<Relation> relationSet;
	
	@Filters({
		@Filter(name = "excludeAdministrativeEntities", condition = "applicationId = -1"),
		@Filter(name = "excludeEverything", condition = "applicationId = -1")
	})
	@OneToMany(mappedBy = "contentApplicationId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.SELECT)
	@OrderBy(value = "applicationId ASC")
	@XmlTransient
	private Set<RelationInline> relationInlineSet;
	
	@Filters({
		@Filter(name = "excludeAdministrativeEntities", condition = "applicationId = -1"),
		@Filter(name = "excludeEverything", condition = "applicationId = -1")
	})
	@OneToMany(mappedBy = "contentApplicationId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.SELECT)
	@OrderBy(value = "applicationId ASC")
	@XmlTransient
	private Set<AnchorInline> anchorInlineSet;
	
	/**
	 * Represents one field in a content item or relation. The element's content model appears to allow almost anything, but in practice 
	 * this is not the case. When importing, the field element content is expected to conform to a field definition identified by the 
	 * name attribute, and will fail to be imported if this is not the case.
	 */
	@OneToMany(mappedBy = "contentApplicationId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.SELECT)
	@XmlElement(name = "field", nillable = true)
	private List<Field> fieldList;
	
	/**
	 * When importing a content item that already exists in the target publication, you can use this element to update the content 
	 * item's source and source ID references
	 */
	@Embedded
	@AttributeOverrides({
        @AttributeOverride(name = "newSource", column = @Column(name = "newSource")),
        @AttributeOverride(name = "newSourceId", column = @Column(name = "newSourceId")),
    })
	@XmlElement(name = "update")
	private Update update;
	
	@Filters({
		@Filter(name = "excludeContributors", condition = "applicationId = -1"),
		@Filter(name = "excludeEverything", condition = "applicationId = -1")
	})
	@OneToMany(mappedBy = "contentApplicationId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.SELECT)
	@OrderBy(value = "applicationId ASC")
	@XmlElement(name = "author")
	private Set<Author> authorSet;
	
	@Filters({
		@Filter(name = "excludeContributors", condition = "applicationId = -1"),
		@Filter(name = "excludeEverything", condition = "applicationId = -1")
	})
	@OneToMany(mappedBy = "contentApplicationId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.SELECT)
	@OrderBy(value = "applicationId ASC")
	@XmlElement(name = "creator")	
	private Set<Creator> creatorSet;
	
	/**
	 * Used to set section priority
	 */
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "priority"))
	@XmlElement(name = "priority")
	private Priority priority;
	
	/**
	 * Elements Id Not Referenced In Escenic's Documentation
	 */
	@Column(name = "uri")
	@XmlElement(name = "uri")
	private String uri;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "problem")
	@XmlTransient
	private ContentProblem contentProblem;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "duplicates")
	@XmlTransient
	private RelationInlineProblem relationInlineProblem;
	
	/**
	 * All work and no play makes Jack a dull boy.
	 */
	@Transient
	@XmlTransient
	private String dull;
	
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
	
	public void setSectionRefSet(Set<SectionRef> sectionRefSet) {
		
		this.sectionRefSet = sectionRefSet;
	}
	
	public Set<SectionRef> getSectionRefSet() {
		
		return sectionRefSet;
	}
	
	public void setRelationSet(Set<Relation> relationSet) {
		
		this.relationSet = relationSet;
	}
	
	public Set<Relation> getRelationSet() {
		
		return relationSet;
	}
	
	public void setRelationInlineSet(Set<RelationInline> relationInlineSet) {
		
		this.relationInlineSet = relationInlineSet;
	}
	
	public Set<RelationInline> getRelationInlineSet() {
		
		return relationInlineSet;
	}
	
	public void setAnchorInlineSet(Set<AnchorInline> anchorInlineSet) {
		
		this.anchorInlineSet = anchorInlineSet;
	}
	
	public Set<AnchorInline> getAnchorInlineSet() {
		
		return anchorInlineSet;
	}	
	
	public void setFieldList(List<Field> fieldList) {
		
		this.fieldList = fieldList;
	}
	
	public List<Field> getFieldList() {
		
		return fieldList;
	}
	
	public void setUpdate(Update update) {
		
		this.update = update;
	}
	
	public Update getUpdate() {
		
		return update;
	}
	
	public void setAuthorSet(Set<Author> authorSet) {
		
		this.authorSet = authorSet;
	}
	
	public Set<Author> getAuthorSet() {
		
		return authorSet;
	}
	
	public void setCreatorSet(Set<Creator> creatorSet) {
		
		this.creatorSet = creatorSet;
	}
	
	public Set<Creator> getCreatorSet() {
		
		return creatorSet;
	}
	
	public void setPriority(Priority priority) {
		
		this.priority = priority;
	}
	
	public Priority getPriority() {
		
		return priority;
	}
	
	public void setUri(String uri) {
		
		this.uri = uri;
	}
	
	public String getUri() {
		
		return uri;
	}
	
	public void setContentProblem(ContentProblem contentProblem) {
		
		this.contentProblem = contentProblem;
	}
	
	public ContentProblem getContentProblem() {
		
		return contentProblem;
	}
	
	public void setRelationInlineProblem(RelationInlineProblem relationInlineProblem) {
		
		this.relationInlineProblem = relationInlineProblem;
	}
	
	public RelationInlineProblem getRelationInlineProblem() {
		
		return relationInlineProblem;
	}

	public void setDull(String dull) {
		
		this.dull = dull;
	}
	
	public String getDull() {
		
		return dull;
	}
	
	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}
}
