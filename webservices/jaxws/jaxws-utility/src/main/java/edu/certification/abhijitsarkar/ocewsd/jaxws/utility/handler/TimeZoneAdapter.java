package edu.certification.abhijitsarkar.ocewsd.jaxws.utility.handler;

import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimeZoneAdapter extends XmlAdapter<String, TimeZone> {

	@Override
	public String marshal(TimeZone timeZone) throws Exception {
		return timeZone.getID();
	}

	@Override
	public TimeZone unmarshal(String timeZoneID) throws Exception {
		TimeZone timeZone = TimeZone.getDefault();
		timeZone.setID(timeZoneID);

		return timeZone;
	}
}
