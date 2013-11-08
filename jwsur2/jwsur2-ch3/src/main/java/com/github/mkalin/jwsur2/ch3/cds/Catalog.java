package com.github.mkalin.jwsur2.ch3.cds;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CATALOG")
@XmlAccessorType(XmlAccessType.FIELD)
public class Catalog {
	@XmlElements(@XmlElement(name = "CD", type = CD.class))
	private List<CD> cds;

	public List<CD> getCds() {
		return cds;
	}

	public void setCds(List<CD> cds) {
		this.cds = cds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cds == null) ? 0 : cds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Catalog other = (Catalog) obj;
		if (cds == null) {
			if (other.cds != null)
				return false;
		} else if (!cds.equals(other.cds))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Catalog [cds=" + cds + "]";
	}
}
