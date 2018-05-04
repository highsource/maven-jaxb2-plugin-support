package test.a;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Test;

import test.c.CType;

public class ATest {
	
	@Test
	public void roundtrip() throws JAXBException {
		
		JAXBContext context = JAXBContext.newInstance(AType.class.getPackage().getName());
		
		final AType a = new AType();
		final CType c = new CType();
		a.setC(c);
		c.setC("A");
		final ObjectFactory objectFactory = new ObjectFactory();
		JAXBElement<AType> aElement = objectFactory.createA(a);
		
		Marshaller marshaller = context.createMarshaller();
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Writer writer = new StringWriter();
		marshaller.marshal(aElement, writer);
		String result = writer.toString();
		System.out.println(result);

		Reader reader = new StringReader(result);
		JAXBElement<AType> unmarshalledAElement = (JAXBElement<AType>) unmarshaller.unmarshal(reader);
		
		Assert.assertEquals(aElement.getValue().getC().getC(), unmarshalledAElement.getValue().getC().getC());
		
		
		
		
		
		
		
		
	}

}
