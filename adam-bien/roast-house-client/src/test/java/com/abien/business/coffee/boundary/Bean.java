package com.abien.business.coffee.boundary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author adam-bien.com
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bean {

	private String name;
	private RoastType type;
	private String blend;

	public Bean(String name, RoastType type, String blend) {
		this.name = name;
		this.type = type;
		this.blend = blend;
	}

	public Bean() {
	}

	public String getName() {
		return name;
	}

	public RoastType getType() {
		return type;
	}

	public String getBlend() {
		return blend;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 97 * hash + (this.type != null ? this.type.hashCode() : 0);
		hash = 97 * hash + (this.blend != null ? this.blend.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Bean other = (Bean) obj;
		if ((this.name == null) ? (other.name != null) : !this.name
				.equals(other.name)) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		if ((this.blend == null) ? (other.blend != null) : !this.blend
				.equals(other.blend)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Bean{" + "name=" + name + ", type=" + type + ", blend=" + blend
				+ '}';
	}
}
