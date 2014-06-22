package gr.twentyfourmedia.syndication.model;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <update
 * 		new-source="text"?
 * 		new-sourceid="text"?
 * 	/>
 */
@Embeddable
@XmlRootElement(name = "update")
@XmlAccessorType(XmlAccessType.FIELD)
public class Update {

	/**
	 * The new source name to be assigned to the content item in the target publication
	 */
	@XmlAttribute(name = "new-source")
	private String newSource;
	
	/**
	 * The new source ID to be assigned to the content item in the target publication
	 */
	@XmlAttribute(name = "new-sourceid")
	private String newSourceId;
	
	public void setNewSource(String newSource) {
		
		this.newSource = newSource;
	}
	
	public String getNewSource() {
		
		return newSource;
	}
	
	public void setNewSourceId(String newSourceId) {
		
		this.newSourceId = newSourceId;
	}
	
	public String getNewSourceId() {
		
		return newSourceId;
	}
}