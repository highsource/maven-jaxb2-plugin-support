package se.kf.xml.util;

import java.util.Collection;

/**
 * A very simple rewrite resolver. If the beginning of an URL matches any of the prefixes, the
 * resolver will attempt to fetch the resource from the classloader (using the path of the URL).
 * 
 * Note: the prefixes should contain the beginning of a URL. Otherwise the subsequent rewrite will fail! 
 * @author Ingemar Allqvist (EX10413)
 *
 */
public class RewritingClassPathResolver extends AbstractClassPathResolver {
	public RewritingClassPathResolver(String interceptPrefix) {
		super(interceptPrefix);
	}

	public RewritingClassPathResolver(Collection<String> prefixes) {
		super(prefixes);
	}

	@Override
	String classPathResourceNameFrom(String prefix, String systemId) {
		String pathInCp = null;
		if (LOCAL_RESOURCE.equals(prefix)) {
			pathInCp = getFilenameOf(systemId);
		}
		else {
			pathInCp = systemId.substring(prefix.length());
		}
		if (!pathInCp.startsWith("/")) {
			pathInCp = "/" + pathInCp;
		}
		return pathInCp;
	}
	
}
