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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
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
 * <options>
 * 		<field>...</field>*
 * </options>
 */
@Entity
@Table(name = "options")
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
public class Options {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "applicationId")
	@XmlTransient
	private Long applicationId;
	
	@OneToOne(mappedBy = "options")
	@XmlTransient
	private Field field;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "optionsFields",
            joinColumns = @JoinColumn(name = "optionsId"),
            inverseJoinColumns = @JoinColumn(name = "fieldId")
    )
	@XmlElement(name = "field")
	private List<Field> optionsFieldList;
	
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
	
	public void setOptionsFieldList(List<Field> optionsFieldList) {
		
		this.optionsFieldList = optionsFieldList;
	}
	
	public List<Field> getOptionsFieldList() {
		
		return optionsFieldList;
	}
	
	public void setApplicationDateUpdated(Calendar applicationDateUpdated) {
		
		this.applicationDateUpdated = applicationDateUpdated;
	}
	
	public Calendar getApplicationDateUpdated() {
		
		return applicationDateUpdated;
	}
}