package utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    Context mActivity;
    SharedPreferences preferences;


    public Utils(Context activity) {
        mActivity = activity;
        preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public void setBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, Boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public void clear_shf() {
        preferences.edit().clear().commit();
    }

    public String getCurrency(String currency) {
        if (currency.equals("GBP")) {
            return Constants.POUND;
            // miles
        } else {
            return Constants.EURO;
            // km
        }
    }

    public String getBothCurrency() {
        return Constants.POUND + "/" + Constants.EURO;
    }
}
