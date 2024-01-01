package com.example.jbsb4.helpers
import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {
    private const val PREFS_NAME = "MyPrefs"
    const val ID_KEY = 1

    fun saveID(context: Context, id: Int) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(ID_KEY.toString(), id).apply()
    }

    fun getID(context: Context): Int {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(ID_KEY.toString(), 0)
    }

    fun clearID(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(ID_KEY.toString()).apply()
    }
}