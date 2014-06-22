package gr.twentyfourmedia.syndication.model;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Syndication Syntax : 
 * <priority
 * 		value="int"?
 * />
 */
@Embeddable
@XmlRootElement(name = "priority")
@XmlAccessorType(XmlAccessType.FIELD)
public class Priority {

	@XmlAttribute(name = "value")
	private Integer value;
	
	public void setValue(Integer value) {
		
		this.value = value;
	}
	
	public Integer getValue() {
		
		return value;
	}
}
