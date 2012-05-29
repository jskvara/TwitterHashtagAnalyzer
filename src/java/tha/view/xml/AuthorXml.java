package tha.view.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author Entity
 */
@XmlRootElement(name = "author")
public class AuthorXml {

	private String name;
	private String city;

	public AuthorXml(String name, String city) {
		this.name = name;
		this.city = city;
	}

	public AuthorXml() {
	}

	@XmlAttribute
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
