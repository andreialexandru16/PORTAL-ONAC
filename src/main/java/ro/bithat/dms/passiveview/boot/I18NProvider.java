package ro.bithat.dms.passiveview.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class I18NProvider implements com.vaadin.flow.i18n.I18NProvider {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(I18NProvider.class);

	public I18NProvider() {
		LOGGER.info(I18NProvider.class.getSimpleName() + " was found..");
	}

	public static final String RESOURCE_BUNDLE_NAME = "i18n";
	private static final Map<String, ResourceBundle> bundles;
	private static Locale defaultLocale = new Locale("ro", "RO");

	private static final List<Locale> providedLocales = Collections
			.unmodifiableList(Arrays.asList( 
					defaultLocale,
					new Locale("en", "EN")
//					,
//					Locale.FRANCE,
//					new Locale("tr", "TR")
			));

	static {
		bundles = providedLocales.stream()
				.collect(Collectors.toMap(l -> l.toString(), l -> ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, l, new UTF8Control())));
	}

	@Override
	public List<Locale> getProvidedLocales() {
		LOGGER.info("DMS-OCR-RC-I18NProvider getProvidedLocales..");
		return providedLocales;
	}

	@Override
	public String getTranslation(String key, Locale locale, Object... params) {
		if (key==null || key.isEmpty()) {
			return key;
		}
		String localeKey = locale.toString();
		if (locale.getCountry() == null || locale.getCountry().isEmpty()) {
			localeKey = localeKey+"_"+localeKey.toUpperCase();
		}
		ResourceBundle rb = bundles.get(localeKey);
		if (rb == null) {
			rb = bundles.get(defaultLocale.toString());
		}

		String msg = null;
		try {
			msg = rb.getString(key);
		} catch (MissingResourceException e) {
			LOGGER.error("missing ressource key (i18n) " + key);
			return "!" + locale.getLanguage() + "." + key;
		}
		if (msg.contains("{")) {
			return new MessageFormat(msg, locale).format(params);
		}
		return msg;
	}

}