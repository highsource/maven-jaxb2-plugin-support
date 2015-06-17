package se.kf.xml.util;

import java.io.InputStream;

public class ClassPathResourceLoader {
	
	InputStream getResourceAsStream(String name) {
		return this.getClass().getResourceAsStream(name);
	}

}
