package in.moneytransfer.ipay.launch.manager;

import android.content.Context;
import android.content.SharedPreferences;

import in.moneytransfer.ipay.launch.utils.FingerPrintConstants;

/**
 * Created by mayankchauhan on 22/09/17.
 */

public class SharedPrefsManager {

    private SharedPreferences preferences;
    private int defIntValue = 0;
    public SharedPrefsManager(Context context) {
        preferences = context.getSharedPreferences(FingerPrintConstants.fingerPrintKeyPrefs,Context.MODE_PRIVATE);
    }
    public void addValue(String key, Boolean printEnabled)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,printEnabled);
        editor.commit();

    }
    public void addPassbookCount(String key,int value)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }
    public int getPassbookCount(String key)
    {
        return preferences.getInt(key,defIntValue);
    }
    public void addStringValue(String key,String value)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();

    }
    public String getStringValue(String key)
    {
        return preferences.getString(key,FingerPrintConstants.DEFAULT_STRING);
    }
    public boolean getValue(String key)
    {
        return preferences.getBoolean(key,false);
    }
}
