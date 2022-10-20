package ro.bithat.dms.service;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class StreamToStringUtil {

	public static String fileToString(String relativePath) {
		String text = null;
	    try (Scanner scanner = new Scanner(StreamToStringUtil.class.getClassLoader().getResourceAsStream(relativePath), StandardCharsets.UTF_8.name())) {
	        text = scanner.useDelimiter("\\A").next();
	    }
	    return text;
	}
}
