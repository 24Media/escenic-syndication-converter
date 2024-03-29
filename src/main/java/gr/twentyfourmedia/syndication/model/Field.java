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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <field
 * 		name="text"
 * 		expand-entities="(true|false)"?
 * 		title="text"?
 * >
 *	text
 * </field>
 * 
 * Note : ((ANYTHING|<relation>...</relation>|text)*|<field>...</field>*|<value>...</value>*)<options>...</options>? Syntax Simplified To text
 */
@Entity
@Table(name = "field")
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
public class Field {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId")
	@XmlTransient
	private Long applicationId;
	
	@ManyToOne
	@JoinColumn(name = "contentApplicationId", referencedColumnName = "applicationId")
	@XmlTransient
	private Content contentApplicationId;
	
	@ManyToOne
	@JoinColumn(name = "relationApplicationId", referencedColumnName = "applicationId")
	@XmlTransient
	private Relation relationApplicationId;

	/**
	 * Identifies the content item/relation field represented by this field element. For import, the value specified must be the name of 
	 * one of the fields defined for the content item/relation in the target publication's content-type resource. The value you specify 
	 * here will then determine  what kind of content your field element may have. If, for example, your field element has the name 
	 * "headline" and belongs to a content element with the type "news", then:
	 * 	1) The content-type resource of the target publication must contain a content-type element with the name "news"
	 * 	2) The "news" content-type element must contain a field element with the name "headline"
	 * 	3) The content of your field element must conform to the "headline" field definition in the content-type resource
	 */
	@Column(name = "name")
	@XmlAttribute(name = "name")
	private String name;
	
	/**
	 * If set to true, then any HTML entities in the field content are expanded during import. If set to false, any entities are retained
	 */
	@Column(name = "expandEntities")
	@XmlAttribute(name = "expand-entities")
	private String expandEntities;
	
	/**
	 * A title associated with the field. This attribute is only used for link fields (fields that are defined in the content-type 
	 * resource as having the type "link"). It is used to hold an alternative name for the resource referenced in the field. It could be 
	 * used, for example, to contain a descriptive title (e.g "London Bridge") for a link field containing the URL of an image file with 
	 * a cryptic auto-generated name (e.g image-store://places/img099345.jpg)
	 */
	@Column(name = "title")
	@XmlAttribute(name = "title")
	@XmlJavaTypeAdapter(value = gr.twentyfourmedia.syndication.utilities.FieldAdapter.class, type = String.class)
	private String title;
	
	@Lob
	@Column(name = "field", columnDefinition = "text")
	@XmlValue
	@XmlJavaTypeAdapter(value = gr.twentyfourmedia.syndication.utilities.FieldAdapter.class, type = String.class)
	private String field;
	
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
	
	public void setRelationApplicationId(Relation relationApplicationId) {
		
		this.relationApplicationId = relationApplicationId;
	}
	
	public Relation getRelationApplicationId() {
		
		return relationApplicationId;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setExpandEntities(String expandEntities) {
		
		this.expandEntities = expandEntities;
	}
	
	public String getExpandEntities() {
		
		return expandEntities;
	}
	
	public void setTitle(String title) {
		
		this.title = title;
	}
	
	public String getTitle() {
		
		return title;
	}

	public void setField(String field) {

		this.field = field;
	}
	
	public String getField() {
	
		return field;
	}

	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}
}
