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
 * <author
 * 		id-ref="text"?
 * 		(source="text" sourceid="text")?
 * 		dbid="text"?
 * 		firstname="text"?
 * 		surname="text"?
 * 		username="text"?
 * 		email-address="text"?
 * />
 */
@Entity
@Table(name = "author")
@XmlRootElement(name = "parent")
@XmlAccessorType(XmlAccessType.FIELD)
public class Author {

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
	 * The id of the author. If this attribute is specified, a person element with an id attribute
	 * that matches this attribute must appear somewhere before this author element in the syndication 
	 * file. If dbid or source and sourceid are specified, then this attribute is ignored
	 */
	@Column(name = "idRef")
	@XmlAttribute(name = "id-ref")
	private String idRef;
	
	/**
	 * The source of the author. If this attribute is specified, then sourceid must also be
	 * specified. One of the following two conditions must be satisfied:
	 * 		1) The target publication must already contain a "person" content item with source and
	 * 		sourceid attributes that match this element's source and sourceid , or
	 * 		2) A person element with source and sourceid attributes that match source and sourceid 
	 * 		must appear somewhere before this author element in the syndication file.
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
	 * The dbid of the author. If this attribute is specified, then one of the following two conditions must be satisfied:
	 * 		1) The target publication must already contain a "person" item with a dbid attribute that matches this attribute, or
	 *		2) A person with a dbid attribute that matches this attribute must appear somewhere before this element in the syndication file.
	 */
	@Column(name = "dbId")
	@XmlAttribute(name = "dbid")
	private String dbId;
	
	/**
	 * The first name of the this author. If this attribute is specified, then one of the following two conditions must be satisfied:
	 *		1) The target publication must already contain a "person" content item with a firstname field that matches this attribute, or
	 * 		2) A person with a firstName child that matches this attribute must appear somewhere before this author in the syndication file.
	 * Using the firstname attribute on its own is not recommended; you should use it in combination with the surname attribute.
	 * If dbid or source and sourceid or id-ref are specified, then this attribute is ignored.
	 */
	@Column(name = "firstName")
	@XmlAttribute(name = "firstname")
	private String firstName;
	
	/**
	 * Using the surname attribute on its own is not recommended; you should use it in combination with the firstname attribute.
	 */
	@Column(name = "surName")
	@XmlAttribute(name = "surname")
	private String surName;

	/**
	 * The username of the this author. If this attribute is specified, then one of the following two conditions must be satisfied:
	 * 		1) The target publication must already contain a "person" content item with a username field that matches this attribute, or
	 * 		2) A person with a username that matches this attribute must appear somewhere before this author in the syndication file.
	 * If dbid or source and sourceid or id-ref are specified, then this attribute is ignored.
	 */
	@Column(name = "userName")
	@XmlAttribute(name = "username")
	private String userName;
	
	/**
	 * The email of the this author. If this attribute is specified, then one of the following two conditions must be satisfied:
	 * 		1) The target publication must already contain a "person" content item with a email field that matches this attribute, or
	 * 		2) A person with a email-address that matches this attribute must appear somewhere before this author in the syndication file.
	 * If dbid or source and sourceid or id-ref are specified, then this attribute is ignored.
	 */
	@Column(name = "emailAddress")
	@XmlAttribute(name = "email-address")
	private String emailAddress;
	
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
	
	public void setFirstName(String firstName) {
		
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		
		return firstName;
	}
	
	public void setSurName(String surName) {
		
		this.surName = surName;
	}
	
	public String getSurName() {
		
		return surName;
	}
	
	public void setUserName(String userName) {
		
		this.userName = userName;
	}
	
	public String getUserName() {
		
		return userName;
	}
	
	public void setEmailAddress(String emailAddress) {
		
		this.emailAddress = emailAddress;
	}
	
	public String getEmailAddress() {
		
		return emailAddress;
	}
	
	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}	
	
}
