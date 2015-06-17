package se.kfm.xml.converters;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.joda.time.DateTime;

public class XSDDateTimeToJodaDateTimeMarshaller {

	public static DateTime unmarshal(String xmlGregorianCalendar) {
		DateTime result = new DateTime(xmlGregorianCalendar);
		return result;
	}

	public static String marshal(DateTime dateTime) {
		String result = "MARSHALLING_ERROR";
		try {
			result = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(dateTime.toGregorianCalendar())
					.toXMLFormat();
		} catch (DatatypeConfigurationException e) {
	    	throw new RuntimeException(e);
		}
		return result;
	}
}