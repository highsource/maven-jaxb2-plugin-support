package se.kf.xml.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;

import com.sun.tools.xjc.reader.xmlschema.parser.LSInputSAXWrapper;

public abstract class AbstractClassPathResolver implements LSResourceResolver {

	protected static final String LOCAL_RESOURCE = "LOCAL";
	protected ClassPathResourceLoader schemaLoader = null;
	protected List<String> interceptPrefixes = null;

	public AbstractClassPathResolver(String interceptPrefix) {
		this.schemaLoader = new ClassPathResourceLoader();
		interceptPrefixes = new ArrayList<String>();
		interceptPrefixes.add(interceptPrefix);
	}

	public AbstractClassPathResolver(Collection<String> prefixes) {
		this.schemaLoader = new ClassPathResourceLoader();
		interceptPrefixes = new ArrayList<String>();
		interceptPrefixes.addAll(prefixes);
	}
	
	/**
	 * Construct a Classpath Resource Name, given the match and the original systemId
	 * @param prefix	the matched prefix
	 * @param systemId	the original systemId
	 * @return
	 */
	abstract String classPathResourceNameFrom(String prefix, String systemId);
	
	public void setSchemaLoader(ClassPathResourceLoader schemaLoader) {
		this.schemaLoader = schemaLoader;
	}

	public LSInput resolveResource(String type, String namespaceURI, String publicId,String systemId, String baseURI) {
		String prefix = matchesAnyPrefix(systemId);
		if (prefix != null) {
			String pathInCp = classPathResourceNameFrom(prefix, systemId);
			InputStream istr = schemaLoader.getResourceAsStream(pathInCp);
			if (null == istr && !(LOCAL_RESOURCE.equals(prefix))) {
				throwAssertionError("Expected to find resource: \"${classPath: %s0}\".",
								pathInCp);
			}
			InputSource is = new InputSource(istr);
			LSInput result = new LSInputSAXWrapper(is);
			return result;
		} else {
			return null;
		}
	}

	protected String matchesAnyPrefix(String systemId) {
		if (null == systemId) {
			return null;
		} else if (systemId.indexOf(":") < 0) {
			// Local Resource. Undecided. Must try a classpath load, but not fail if not found...
			return LOCAL_RESOURCE;
		} else {
			for (String prefix : interceptPrefixes) {
				if (systemId.startsWith(prefix)) {
					return prefix;
				}
			}
			return null;
		}
	}

	void throwAssertionError(String msg) {
		throw new RuntimeException(msg);
	}

	void throwAssertionError(String msg, Object... objects) {
		String actualMessage = String.format(msg, objects);
		throw new RuntimeException(actualMessage);
	}

	String getFilenameOf(String systemId) {
		String pathInCp;
		int lastDash = systemId.lastIndexOf('/');
		if (lastDash < 0) {
			pathInCp = "/" + systemId;
		} else {
			pathInCp = systemId.substring(lastDash);
		}
		return pathInCp;
	}

}

