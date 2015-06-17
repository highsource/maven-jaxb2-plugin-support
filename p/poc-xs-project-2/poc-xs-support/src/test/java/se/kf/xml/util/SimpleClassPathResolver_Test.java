package se.kf.xml.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.w3c.dom.ls.LSInput;

public class SimpleClassPathResolver_Test {

	@Test
	public void test_systemId_http() {
		String content = "DUMMY";
		ClassPathResourceLoader loader = mock(ClassPathResourceLoader.class);
		
		InputStream dummyIS = new ByteArrayInputStream(content.getBytes());
		when(loader.getResourceAsStream(any(String.class))).thenReturn(dummyIS);

		AbstractClassPathResolver resolver = new SimpleClassPathResolver("http://kfm.se/");
		resolver.setSchemaLoader(loader);
		LSInput result = resolver.resolveResource("schema", null, "http://mynamespace", "http://kfm.se/poc/schemas/common/MySchema.xsd", null);
		verify(loader).getResourceAsStream(eq("/MySchema.xsd"));
		assertNotNull(result);
	}

	@Test
	public void test_systemId_http_2() {
		String content = "DUMMY";
		ClassPathResourceLoader loader = mock(ClassPathResourceLoader.class);
		
		InputStream dummyIS = new ByteArrayInputStream(content.getBytes());
		when(loader.getResourceAsStream(any(String.class))).thenReturn(dummyIS);

		AbstractClassPathResolver resolver = new SimpleClassPathResolver("http://kfm.se/poc");
		resolver.setSchemaLoader(loader);
		LSInput result = resolver.resolveResource("schema", null, "http://mynamespace", "http://kfm.se/poc/schemas/common/MySchema.xsd", null);
		verify(loader).getResourceAsStream(eq("/MySchema.xsd"));
		assertNotNull(result);
	}

	
	@Test
	public void test_systemId_file_remote() {
		String content = "DUMMY";
		ClassPathResourceLoader loader = mock(ClassPathResourceLoader.class);
		
		InputStream dummyIS = new ByteArrayInputStream(content.getBytes());
		when(loader.getResourceAsStream(any(String.class))).thenReturn(dummyIS);

		Set<String> prefixes = new HashSet<String>();
		prefixes.add("file://kfm.se/");
		prefixes.add("http://kfm.se/");
		AbstractClassPathResolver resolver = new SimpleClassPathResolver(prefixes);
		resolver.setSchemaLoader(loader);
		LSInput result = resolver.resolveResource("schema", null, "http://mynamespace", "file://kfm.se/schemas/common/MySchema.xsd", null);
		verify(loader).getResourceAsStream(eq("/MySchema.xsd"));
		assertNotNull(result);
	}

	@Test
	public void test_systemId_file_noMatch() {
		String content = "DUMMY";
		ClassPathResourceLoader loader = mock(ClassPathResourceLoader.class);
		
		InputStream dummyIS = new ByteArrayInputStream(content.getBytes());
		when(loader.getResourceAsStream(any(String.class))).thenReturn(dummyIS);

		Set<String> prefixes = new HashSet<String>();
		prefixes.add("file://kfm.se/");
		prefixes.add("http://kfm.se/");
		AbstractClassPathResolver resolver = new SimpleClassPathResolver(prefixes);
		resolver.setSchemaLoader(loader);
		LSInput result = resolver.resolveResource("schema", null, "http://mynamespace", "file://kfm2.se/schemas/common/MySchema.xsd", null);
		verify(loader, never()).getResourceAsStream(any(String.class));
		assertNull(result);;
	}

	@Test
	public void test_systemId_file_localPathAbsolute() {
		String content = "DUMMY";
		ClassPathResourceLoader loader = mock(ClassPathResourceLoader.class);
		
		InputStream dummyIS = new ByteArrayInputStream(content.getBytes());
		when(loader.getResourceAsStream(any(String.class))).thenReturn(dummyIS);

		Set<String> prefixes = new HashSet<String>();
		prefixes.add("file://kfm.se/");
		prefixes.add("http://kfm.se/");
		AbstractClassPathResolver resolver = new SimpleClassPathResolver(prefixes);
		resolver.setSchemaLoader(loader);
		LSInput result = resolver.resolveResource("schema", null, "http://mynamespace", "/schemas/common/MySchema.xsd", null);
		verify(loader).getResourceAsStream(eq("/MySchema.xsd"));
		assertNotNull(result);;
	}

	@Test
	public void test_systemId_file_localPathRelative() {
		String content = "DUMMY";
		ClassPathResourceLoader loader = mock(ClassPathResourceLoader.class);
		
		InputStream dummyIS = new ByteArrayInputStream(content.getBytes());
		when(loader.getResourceAsStream(any(String.class))).thenReturn(dummyIS);

		Set<String> prefixes = new HashSet<String>();
		prefixes.add("file://kfm.se/");
		prefixes.add("http://kfm.se/");
		AbstractClassPathResolver resolver = new SimpleClassPathResolver(prefixes);
		resolver.setSchemaLoader(loader);
		LSInput result = resolver.resolveResource("schema", null, "http://mynamespace", "/schemas/common/MySchema.xsd", null);
		verify(loader).getResourceAsStream(eq("/MySchema.xsd"));
		assertNotNull(result);;
	}
	
}
