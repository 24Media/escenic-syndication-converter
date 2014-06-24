package gr.twentyfourmedia.syndication.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <escenic
 * 		version="2.0"
 * >
 * 		<content>...</content>*
 * 		<link>...</link>*
 * 		<person>...</person>*
 * 		<multimedia-group>...</multimedia-group>*
 * 		<section>...</section>*
 * 		<list>...</list>*
 * 		<inbox>...</inbox>*
 * 		<section-page>...</section-page>*
 * </escenic>
 * 
 *  Note : Use of <link /> element is deprecated. It is only retained for reasons of backwards compatibility.
 */
@XmlRootElement(name = "escenic")
@XmlAccessorType(XmlAccessType.FIELD)
public class Escenic {

	@XmlAttribute(name = "version")
	private String version;
	
	@XmlElement(name = "content")
	private List<Content> contentList;
	
	@XmlElement(name = "section")
	private List<Section> sectionList;
	
	public void setVersion(String version) {
		
		this.version = version;
	}
	
	public String getVersion() {
	
		return version;
	}
	
	public void setContentList(List<Content> contentList) {
		
		this.contentList = contentList;
	}
	
	public List<Content> getContentList() {
	
		return contentList;
	}
	
	public void setSectionList(List<Section> sectionList) {
		
		this.sectionList = sectionList;
	}
	
	public List<Section> getSectionList() {
		
		return sectionList;
	}
}
