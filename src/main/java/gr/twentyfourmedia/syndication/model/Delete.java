package gr.twentyfourmedia.syndication.model;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <delete
 * 		recursive="(true|false)"?
 * 		delete-content="(true|false)"?
 * 		move-sections="(true|false)"?
 * />
 */
@Embeddable
@XmlRootElement(name = "delete")
@XmlAccessorType(XmlAccessType.FIELD)
public class Delete {

	/**
	 * If true, then all of the section's subsections and content items will be deleted with it
	 */
	@XmlAttribute(name = "recursive")
	private String recursive;
	
	/**
	 * If true, then all of the section's content items will be deleted with it
	 */
	@XmlAttribute(name = "delete-content")
	private String deleteContent;
	
	/**
	 * If true, then all of the section's subsections will be moved to this section's parent section
	 */
	@XmlAttribute(name = "move-content")
	private String moveContent;
	
	public void setRecursive(String recursive) {
		
		this.recursive = recursive;
	}
	
	public String getRecursive() {
		
		return recursive;
	}
	
	public void setDeleteContent(String deleteContent) {
		
		this.deleteContent = deleteContent;
	}
	
	public String getDeleteContent() {
		
		return deleteContent;
	}
	
	public void setMoveContent(String moveContent) {
		
		this.moveContent = moveContent;
	}
	
	public String getMoveContent() {
		
		return moveContent;
	}
}
