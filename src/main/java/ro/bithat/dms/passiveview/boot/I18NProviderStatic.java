package ro.bithat.dms.passiveview.boot;

import com.vaadin.flow.component.UI;
import ro.bithat.dms.boot.BeanUtil;

import java.util.Locale;

public class I18NProviderStatic {

	public static String getTranslation(String key, Object... params) {
		Locale locale = UI.getCurrent().getLocale();
		return getTranslation(key, locale, params);
	}
	
	public static String getTranslation(String key, Locale locale, Object... params) {
		return BeanUtil.getBean(I18NProvider.class).getTranslation(key, locale, params);
	}
}
