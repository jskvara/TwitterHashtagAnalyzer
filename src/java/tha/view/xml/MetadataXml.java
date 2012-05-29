package tha.view.xml;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Metadata entity
 *
 * Example:
<?xml version="1.0" encoding="UTF-8"?>
<metadata>
	<name>DBLP data</name>
	<description>data about researches</description>
	<lastUpdate>2011-02-03T20:26:00.501Z</lastUpdate>
	<updateInterval>10080</updateInterval>
	<authors>
		<author name="name1" city="city2" />
		<author name="name2" city="city1" />
	</authors>
</metadata>
 */
@XmlRootElement(name = "metadata")
@XmlType(propOrder = {"name", "description", "lastUpdate", "updateInterval", "authors"})
public class MetadataXml {

	private String name;
	private String description;
	private Date lastUpdate;
	private Integer updateInterval;
	private List<AuthorXml> authors;

	public MetadataXml(String name, String description, Date lastUpdate, int updateInterval, List<AuthorXml> authors) {
		this.name = name;
		this.description = description;
		this.lastUpdate = lastUpdate;
		this.updateInterval = updateInterval;
		this.authors = authors;
	}

	public MetadataXml() {
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "author")
	@XmlElementWrapper(name = "authors")
	public List<AuthorXml> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorXml> authors) {
		this.authors = authors;
	}

	@XmlElement
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public int getUpdateInterval() {
		return updateInterval;
	}

	public void setUpdateInterval(Integer updateInterval) {
		this.updateInterval = updateInterval;
	}
}
