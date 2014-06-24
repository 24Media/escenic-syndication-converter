package gr.twentyfourmedia.syndication.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * (ANYTHING|<relation>...</relation>|text)*
 */
@Entity
@Table(name = "fieldElements")
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
public class FieldElement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId")
	@XmlTransient
	private Long applicationId;
	
	@ManyToOne
	@JoinColumn(name = "fieldApplicationId", referencedColumnName = "applicationId", nullable = false)
	@XmlTransient
	private Field fieldApplicationId;
	
	/**
	 * This placeholder can be replaced by any element. This is mainly intended to allow you to enter HTML elements 
	 * in the body of field elements. You can, however, insert elements from any other namespace if you have the need
	 */
	@Lob
	@Column(name = "anything")
	private String anything;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "relation", referencedColumnName = "applicationId", nullable = true)
	@XmlElement(name = "relation")
	private Relation relation;
	
	@Lob
	@Column(name = "text")
	private String text;
	
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
	
	public void setFieldApplicationId(Field fieldApplicationId) {
		
		this.fieldApplicationId = fieldApplicationId;
	}
	
	public Field getFieldApplicationId() {
		
		return fieldApplicationId;
	}
	
	public void setAnything(String anything) {
		
		this.anything = anything;
	}
	
	public String getAnything() {
		
		return anything;
	}
	
	public void setRelation(Relation relation) {
		
		this.relation = relation;
	}
	
	public Relation getRelation() {
		
		return relation;
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
