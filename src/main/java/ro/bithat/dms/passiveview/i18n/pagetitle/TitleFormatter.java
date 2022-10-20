package ro.bithat.dms.passiveview.i18n.pagetitle;

import java.util.Locale;

import static ro.bithat.dms.passiveview.boot.I18NProviderStatic.getTranslation;


public class TitleFormatter {
 public String format(String key, Locale locale){
 return getTranslation("global.app.name" , locale)
 + " | "
 + getTranslation(key , locale);
 }
}