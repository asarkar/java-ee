package com.github.mkalin.jwsur2.ch4.rand.client.async;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "nextNResponse")
public class MyNextNResponse {

	@XmlElement(name = "return", nillable = true)
	protected List<Integer> _return;

	public List<Integer> getReturn() {
		if (_return == null) {
			_return = new ArrayList<Integer>();
		}
		return this._return;
	}
}
