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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <value>
 * 		text
 * </value>
 */
@Entity
@Table(name = "value")
@XmlRootElement(name = "value")
@XmlAccessorType(XmlAccessType.FIELD)
public class Value {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId")
	@XmlTransient
	private Long applicationId;
	
	@ManyToOne
	@JoinColumn(name = "fieldApplicationId", referencedColumnName = "applicationId", nullable = false)
	@XmlTransient
	private Field fieldApplicationId;
	
	@Column(name = "value")
	@XmlElement(name = "value")
	private String value;
	
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

	public void setValue(String value) {
		
		this.value = value;
	}
	
	public String getValue() {
		
		return value;
	}
	
	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}

	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}
}
