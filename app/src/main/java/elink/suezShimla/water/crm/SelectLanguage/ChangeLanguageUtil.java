package elink.suezShimla.water.crm.SelectLanguage;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class ChangeLanguageUtil {

    public static void yourLanguage(Context context, String languageToLoad) {
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
}
