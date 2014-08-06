package gr.twentyfourmedia.syndication.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

//TODO Author and Creator Are Identical, Move contentApplicationId To Contributor
@Entity
@DiscriminatorValue(value = "creator")
@XmlRootElement(name = "parent")
@XmlAccessorType(XmlAccessType.FIELD)
public class Creator extends Contributor {

	@ManyToOne
	@JoinColumn(name = "contentApplicationId", referencedColumnName = "applicationId")
	@XmlTransient
	private Content contentApplicationId;
	
	public void setContentApplicationId(Content contentApplicationId) {
		
		this.contentApplicationId = contentApplicationId;
	}
	
	public Content getContentApplicationId() {
		
		return contentApplicationId;
	}
}