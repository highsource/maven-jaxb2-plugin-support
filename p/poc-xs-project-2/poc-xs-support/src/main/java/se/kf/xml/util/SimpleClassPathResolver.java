package se.kf.xml.util;

import java.util.Collection;

/**
 * A very simple rewrite resolver. If the beginning of an URL matches any of the
 * prefixes, the resolver will attempt to fetch the resource from the
 * classloader (using the file name of the URL).
 * 
 * Note: the prefixes should contain the beginning of a URL. Otherwise the
 * subsequent rewrite will fail!
 * 
 * @author Ingemar Allqvist (EX10413)
 * 
 */
public class SimpleClassPathResolver extends AbstractClassPathResolver {
	public SimpleClassPathResolver(String interceptPrefix) {
		super(interceptPrefix);
	}

	public SimpleClassPathResolver(Collection<String> prefixes) {
		super(prefixes);
	}

	@Override
	String classPathResourceNameFrom(String matchedPrefix, String systemId) {
		String pathInCp = getFilenameOf(systemId);
		return pathInCp;
	}
}
