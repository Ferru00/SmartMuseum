package com.example.smart_museum;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SaveSharedPreference {
    static final String PREF_USER_SESSION_COOKIE= "PREF_USER_SESSION_COOKIE";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserCookie(Context ctx, String cookie)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_SESSION_COOKIE, cookie);
        editor.commit();
    }

    public static String getUserCookie(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_SESSION_COOKIE, "NaV");
    }
}
