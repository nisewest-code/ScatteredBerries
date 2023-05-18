package com.scatt.eredbe.rries.utils.storage

import android.content.Context
import android.content.SharedPreferences
import com.scatt.eredbe.rries.utils.string.Pref
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class PrefScatteredBerries {

    companion object {
        private var mPreference: SharedPreferences? = null
        suspend fun initPref(context: Context) {
            coroutineScope {
                val param = async {
                    context.applicationContext
                        .getSharedPreferences(
                            Pref.getPrefKeyAppName(),
                            Context.MODE_PRIVATE
                        )
                }
                mPreference = param.await()
            }
        }

        fun saveStartUrl(url: String?) {
            mPreference!!.edit()
                .putString(Pref.getPrefKeyStartUrl(), url)
                .apply()
        }

        fun getStartUrl(): String {
            return mPreference?.getString(
                Pref.getPrefKeyStartUrl(),
                ""
            ) ?: ""
        }

        fun saveLastUrl(url: String?) {
            mPreference!!.edit()
                .putString(Pref.getPrefKeyLastUrl(), url)
                .apply()
        }

        fun getLastUrl(): String {

            return mPreference?.getString(
                Pref.getPrefKeyLastUrl(),
                ""
            ) ?: ""
        }

        fun saveStatus(url: String?) {

            mPreference!!.edit()
                .putString(Pref.getPrefKeyStatus(), url)
                .apply()
        }

        fun getStatus(): String {

            return mPreference!!.getString(
                Pref.getPrefKeyStatus(),
                ""
            ) ?: ""
        }

        fun saveCampaign(url: String?) {

            mPreference!!.edit()
                .putString(Pref.getPrefKeyCampaign(), url)
                .apply()
        }

        fun getCampaign(): String {

            return mPreference!!.getString(
                Pref.getPrefKeyCampaign(),
                ""
            ) ?: ""
        }

        fun saveFbclid(string: String?) {

            mPreference!!.edit()
                .putString(Pref.getPrefKeyFbclid(), string)
                .apply()
        }

        fun getFbclid(): String {

            return mPreference!!.getString(
                Pref.getPrefKeyFbclid(),
                "null"
            ) ?: "null"
        }

        fun savePixelId(string: String?) {
            mPreference!!.edit()
                .putString(Pref.getPrefKeyPixelId(), string)
                .apply()
        }

        fun getPixelId(): String {

            return mPreference!!.getString(
                Pref.getPrefKeyPixelId(),
                "null"
            ) ?: "null"
        }

        fun saveMusic(value: Boolean){


            mPreference!!.edit().putBoolean(Pref.getPrefMusic(), value).apply()
        }

        fun isMusic(): Boolean {

            return mPreference!!.getBoolean(
                Pref.getPrefMusic(),
                false
            ) ?: false
        }
    }

}