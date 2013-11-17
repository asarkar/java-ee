package com.abien.business.coffee.entity;

import java.util.Objects;

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

	public void setName(String name) {
		this.name = name;
	}

	public void setType(RoastType type) {
		this.type = type;
	}

	public void setBlend(String blend) {
		this.blend = blend;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 79 * hash + Objects.hashCode(this.name);
		hash = 79 * hash + (this.type != null ? this.type.hashCode() : 0);
		hash = 79 * hash + Objects.hashCode(this.blend);
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
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		if (!Objects.equals(this.blend, other.blend)) {
			return false;
		}
		return true;
	}
}
