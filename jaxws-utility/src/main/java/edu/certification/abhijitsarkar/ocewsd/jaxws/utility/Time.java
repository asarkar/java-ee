package edu.certification.abhijitsarkar.ocewsd.jaxws.utility;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import edu.certification.abhijitsarkar.ocewsd.jaxws.utility.handler.TimeZoneAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "time")
public class Time implements Serializable {
	private static final long serialVersionUID = 7091303500596917885L;

	public Time() {
		this(Calendar.getInstance(), TimeZone.getDefault());
	}

	public Time(Calendar time) {
		this(time, TimeZone.getDefault());
	}

	public Time(Calendar time, TimeZone timeZone) {
		this.time = time;
		this.timeZone = timeZone;
	}

	private Calendar time;

	@XmlJavaTypeAdapter(TimeZoneAdapter.class)
	private TimeZone timeZone;

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
}
