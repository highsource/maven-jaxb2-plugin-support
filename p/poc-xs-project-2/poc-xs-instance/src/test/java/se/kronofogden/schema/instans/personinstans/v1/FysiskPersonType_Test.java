package se.kronofogden.schema.instans.personinstans.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;

import se.kf.xml.util.RewritingClassPathResolver;
import se.kf.xml.util.SimpleClassPathResolver;

public class FysiskPersonType_Test {

	private static final String PNR_1 = "7510231231";
	private static final String PNR_2 = "7102112336";
	private static final String PNR_3 = "5804184322";

	@Test
	public void test_validateSchema() throws Throwable{
		InputStream schemaStr = FysiskPersonTYPE.class.getResourceAsStream("/PersonINSTANSv1.xsd");
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		sf.setResourceResolver(new SimpleClassPathResolver("http://kfmit-utv-test.rsvc.se/"));
		sf.newSchema(new StreamSource(schemaStr));
	}
	
	@Test
	public void test_marshal() throws Throwable {
		FysiskPersonTYPE obj = new ObjectFactory().createFysiskPersonTYPE();
		obj.setPersonOrganisationsnummer(PNR_1);
		obj.setTelefonnummer("0733519403");
		obj.setSkyddadePersonuppgifter(true);
		JAXBElement<FysiskPersonTYPE> person = new ObjectFactory().createFysiskPerson(obj);
		JAXBContext cntxt = JAXBContext.newInstance("se.kronofogden.schema.instans.personinstans.v1");
		Marshaller marshal = cntxt.createMarshaller();

		marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		Writer wrt = new StringWriter();
		marshal.marshal(person,  wrt);
	}

	@Test
	public void test_unmarshal() throws Throwable {
		InputStream str = getClass().getClassLoader().getResourceAsStream("test_person.xml");
		InputStream schemaStr = FysiskPersonTYPE.class.getResourceAsStream("/PersonINSTANSv1.xsd");
		JAXBContext cntxt = JAXBContext.newInstance("se.kronofogden.schema.instans.personinstans.v1");
		Unmarshaller unmarsh = cntxt.createUnmarshaller();

		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		sf.setResourceResolver(new SimpleClassPathResolver("http://kfmit-utv-test.rsvc.se/"));
		Schema schema = sf.newSchema(new StreamSource(schemaStr));
		unmarsh.setSchema(schema);
		JAXBElement<FysiskPersonTYPE> obj = (JAXBElement<FysiskPersonTYPE>) unmarsh.unmarshal(str);
		assertNotNull(obj);
		FysiskPersonTYPE person = obj.getValue();
		assertNotNull(person);
		assertEquals("Felix Hermann", person.getNamn().get(0));
	}
}
