package gr.twentyfourmedia.syndication.model;

import java.util.Calendar;

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
 * Syndication Syntax :
 * 
 * <section
 *		id="text"?
 *		(source="text" sourceid="text")?
 *		dbid="text"?
 *		exported-dbid="text"?
 *		name="text"?
 *		unique-name="text"?
 *		mirror-source="(true|false)"?
 *	>
 * 		<delete/>?
 * 		<parent/>?
 * 		<mirror-source/>?
 *		<unique-name>...</unique-name>?
 *		<directory>...</directory>?
 * 		<section-layout>...</section-layout>?
 *		<article-layout>...</article-layout>?
 *		<priority/>?
 *	</section>
 */
@Entity
@Table(name = "section")
@XmlRootElement(name = "section")
@XmlAccessorType(XmlAccessType.FIELD)
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId")
	@XmlTransient
	private Long applicationId;
	
	/**
	 * A unique identifier for this section element. It is only valid and unique within the
	 * current syndication file and can be used to enable the establishment of relationships
	 * between elements in the file. Other elements in the file have id-ref attributes that can be
	 * used to reference section elements.The id attribute is not imported along with sections. 
	 * Unless a dbid has been specified, all sections are assigned new internal IDs during import
	 */
	@Column(name = "id")
	@XmlAttribute(name = "id")
	private String id;
	
	/**
	 * The name of the system from which this section originates. Together with the sourceid
	 * attribute it forms a globally unique external identifier for the section that can be used
	 * for establishing relationships between elements in the syndication file. If
	 * this attribute is specified then a sourceid attribute must also be specified. If a section
	 * element does not have a source and sourceid attribute then it must have either a
	 * dbid attribute or an id attribute. A section element may have several or all of these
	 * attributes, in which case any of them can be used for establishing relationships.
	 * If supplied, source and sourceid are imported and stored with sections. If source and
	 * sourceid are supplied and dbid is not supplied, then they are used to lookup an existing
	 * section. If a section with matching source and sourceid is found, then this section is
	 * updated; otherwise a new section is created. If supplied, source and sourceid are imported 
	 * and stored when creating new sections, but not when updating existing sections.
	 */
	@Column(name = "source")
	@XmlAttribute(name = "source")
	private String source;
	
	/**
	 * Together with the source attribute it forms a globally unique external identifier for the section
	 */
	@Column(name = "sourceId")
	@XmlAttribute(name = "sourceid")
	private String sourceId;
	
	/**
	 * The internal Content Engine ID of this section, which can be used when importing
	 * updated versions of existing content items. It can also be used for establishing
	 * relationships between elements in the syndication file. You should only use the 
	 * dbid attribute when importing updated versions of existing sections. This attribute 
	 * is never present in syndication files that have been exported from a database. 
	 * The ID is always written to the exported-dbid attribute in exported syndication files.
	 */
	@Column(name = "dbId")
	@XmlAttribute(name = "dbid")
	private String dbId;
	
	/**
	 * The internal Content Engine ID of this section, which can be used to identify the section in the
	 * database from which it was exported. This attribute is generated during export but ignored during import.
	 */
	@Column(name = "exportedDbId")
	@XmlAttribute(name = "exported-dbid")
	private String exportedDbId;
	
	/**
	 * The name of this section
	 */
	@Column(name = "name")
	@XmlAttribute(name = "name")
	private String name;
	
	/**
	 * The unique-name or name of an existing section to be updated. You can only use this
	 * attribute for look-up purposes, not for setting a section's unique name. To set the 
	 * unique name of a section you are creating or updating, use the child unique-name element. 
	 * If this attribute is specified, then one of the following conditions must be satisified:
	 * 		1) The target publication must already contain a section with a uniquename or name 
	 * 		attribute that matches this attribute, or
	 * 		2) A section element with a unique-name or name attribute that matches this attribute 
	 * 		must appear somewhere before this section element in the syndication file.
	 * If this is not the case, or if there is a matching name attribute but it is not unique, then
	 * import will fail. If dbid or source and sourceid or id are specified, then this attribute is ignored.
	 */
	@Column(name = "uniqueNameAttribute")
	@XmlAttribute(name = "unique-name")
	private String uniqueNameAttribute;
	
	/**
	 * If true, this section may be mirrored. It may not be set to true if the section has a child mirror-source element
	 */
	@Column(name = "mirrorSourceAttribute")
	@XmlAttribute(name = "mirror-source")
	private String mirrorSourceAttribute;
	
	/**
	 * Used to specify that the section referenced by this element's parent section is to be deleted
	 * from the target publication. The section can only be deleted if either: recursive is set to true OR
	 * Both of the following conditions are satisfied: 1) It contains no child sections or move-sections is set 
	 * to true AND 2) It is not the home section of any content items or delete-content is set to true
	 */
	@Embedded
	@AttributeOverrides({
        @AttributeOverride(name = "recursive", column = @Column(name = "recursive")),
        @AttributeOverride(name = "deleteContent", column = @Column(name = "deleteContent")),
        @AttributeOverride(name = "moveContent", column = @Column(name = "moveContent"))
    })
	@XmlElement(name = "delete")
	private Delete delete;
	
	/**
	 * A reference to this section's parent section
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent", referencedColumnName = "applicationId", nullable = true)
	@XmlElement(name = "parent")
	private Parent parent;
	
	/**
	 * A reference to a section that this element's owning section is to mirror. The owning section
	 * has no content of its own, but just mirrors the content of the section referenced here. The
	 * owning section's mirror-source attribute may not be set to true .
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mirrorSourceElement", referencedColumnName = "applicationId", nullable = true)
	@XmlElement(name = "mirror-source")
	private MirrorSource mirrorSourceElement;
	
	/**
	 * A unique name to be assigned to the section created or updated by this element's parent section element
	 */
	@Column(name = "uniqueNameElement")
	@XmlElement(name = "unique-name")
	private String uniqueNameElement;
	
	/**
	 * A section directory name. It is not really used as a directory name but is used as a component of the section URL
	 */
	@Column(name = "directory")
	@XmlElement(name = "directory")
	private String directory;
	
	/**
	 * The name of the section layout to use for the section
	 */
	@Column(name = "sectionLayout")
	@XmlElement(name = "section-layout")
	private String sectionLayout;
	
	/**
	 * The name of the article layout to use for the section
	 */
	@Column(name = "articleLayout")
	@XmlElement(name = "article-layout")
	private String articleLayout;
	
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
	
	public void setName(String name) {
		
		this.name = name;
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setUniqueNameAttribute(String uniqueNameAttribute) {
		
		this.uniqueNameAttribute = uniqueNameAttribute;
	}
	
	public String getUniqueNameAttribute() {
		
		return uniqueNameAttribute;
	}
	
	public void setMirrorSourceAttribute(String mirrorSourceAttribute) {
		
		this.mirrorSourceAttribute = mirrorSourceAttribute;
	}
	
	public String getMirrorSourceAttribute() {
		
		return mirrorSourceAttribute;
	}
	
	public void setDelete(Delete delete) {
		
		this.delete = delete;
	}
	
	public Delete getDelete() {
		
		return delete;
	}
	
	public void setParent(Parent parent) {
		
		this.parent = parent;
	}
	
	public Parent getParent() {
		
		return parent;
	}
	
	public void setMirrorSourceElement(MirrorSource mirrorSourceElement) {
		
		this.mirrorSourceElement = mirrorSourceElement;
	}
	
	public MirrorSource getMirrorSourceElement() {
		
		return mirrorSourceElement;
	}
	
	public void setUniqueNameElement(String uniqueNameElement) {
		
		this.uniqueNameElement = uniqueNameElement;
	}
	
	public String getUniqueNameElement() {
		
		return uniqueNameElement;
	}
	
	public void setDirectory(String directory) {
		
		this.directory = directory;
	}
	
	public String getDirectory() {
		
		return directory;
	}
	
	public void setSectionLayout(String sectionLayout) {
		
		this.sectionLayout = sectionLayout;
	}
	
	public String getSectionLayout() {
		
		return sectionLayout;
	}
	
	public void setArticleLayout(String articleLayout) {
		
		this.articleLayout = articleLayout;
	}
	
	public String getArticleLayout() {
		
		return articleLayout;
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
