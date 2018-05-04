package test.b;

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

public class BTest {

	@Test
	public void roundtrip() throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(BType.class.getPackage().getName());

		final BType b = new BType();
		final CType c = new CType();
		b.setC(c);
		c.setC("B");
		final ObjectFactory objectFactory = new ObjectFactory();
		JAXBElement<BType> aElement = objectFactory.createB(b);

		Marshaller marshaller = context.createMarshaller();
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Writer writer = new StringWriter();
		marshaller.marshal(aElement, writer);
		String result = writer.toString();
		System.out.println(result);

		Reader reader = new StringReader(result);
		JAXBElement<BType> unmarshalledAElement = (JAXBElement<BType>) unmarshaller.unmarshal(reader);

		Assert.assertEquals(aElement.getValue().getC().getC(), unmarshalledAElement.getValue().getC().getC());

	}

}
