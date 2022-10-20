package ro.bithat.dms.passiveview.boot;

import com.vaadin.flow.component.UI;

import java.util.Locale;

public final class UiI18nUtil {

    public static void setLocale(Locale locale) {
        if(locale != null) {
            UI.getCurrent().getSession().setLocale(locale);
//            UI.getCurrent().access(() -> {
//                UI.getCurrent().setLocale(locale);
//                UI.getCurrent().getSession().setAttribute("selectedLanguage", locale.getLanguage().toUpperCase());
//            });
        }
    }


}
