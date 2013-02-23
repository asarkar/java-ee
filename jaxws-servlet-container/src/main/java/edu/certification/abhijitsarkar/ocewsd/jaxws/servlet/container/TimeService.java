package edu.certification.abhijitsarkar.ocewsd.jaxws.servlet.container;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

@WebService(name = "TimeServiceServletContainer", serviceName = "TimeServiceServletContainer", portName = "TimeServiceServletContainerPort", endpointInterface = "edu.certification.abhijitsarkar.ocewsd.jaxws.servlet.container.TimeServiceSEI")
public class TimeService implements TimeServiceSEI {
	private static final List<String> availableIDs = Arrays.asList(TimeZone
			.getAvailableIDs());

	@WebMethod
	@Override
	public Date getCurrentTime(
			@WebParam final String timeZoneID)
			throws UnsupportedTimeZoneException {
		/* In reality, logging should be done non-intrusively, preferably using a handler. */
		printDebugInfo();

		return getCurrentTimeByTimZoneID(timeZoneID);
	}

	private void printDebugInfo() {
		MessageContext msgCtx = context.getMessageContext();

		Set<Entry<String, Object>> mappings = msgCtx.entrySet();

		if (mappings == null || mappings.size() == 0) {
			return;
		}

		String key = null;
		Object value = null;
		Iterator<Entry<String, Object>> it = mappings.iterator();
		Entry<String, Object> mapping = null;

		while (it.hasNext()) {
			mapping = it.next();

			if (mapping == null) {
				continue;
			}

			key = mapping.getKey();
			value = mapping.getValue();

			if (key == null || value == null) {
				continue;
			}

			logger.debug("[key = " + key + ", value = " + value
					+ ", classname = "
					+ mapping.getValue().getClass().getName() + "]");
		}
	}

	private Date getCurrentTimeByTimZoneID(final String timeZoneID)
			throws UnsupportedTimeZoneException {
		if (availableIDs.contains(timeZoneID)) {
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneID);

			logger.debug("Returning time for time zone ID" + timeZoneID);

			return Calendar.getInstance(timeZone).getTime();
		}

		logger.error("The time zone ID " + timeZoneID + " is not supported.");

		throw new UnsupportedTimeZoneException("The time zone ID " + timeZoneID
				+ " is not supported.");
	}

	@Resource
	private WebServiceContext context;
	private static final Logger logger = AppLogger
			.getInstance(TimeService.class);
}
