package se.kfm.xml.converters;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.joda.time.LocalDate;

public class XSDDateToJodaLocalDateMarshaller {

public static LocalDate unmarshal(String xmlGregorianCalendar) {
    return new LocalDate(xmlGregorianCalendar);
}

public static String marshal(LocalDate localDate)  {
   String result = "MARSHALLING_ERROR";
    try {
        result = DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toDateTimeAtStartOfDay().toGregorianCalendar()).toXMLFormat();
    } catch (DatatypeConfigurationException e) {
    	throw new RuntimeException(e);
    }
    return result;
}
}