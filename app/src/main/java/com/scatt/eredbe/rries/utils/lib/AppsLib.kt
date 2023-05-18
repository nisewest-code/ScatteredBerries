package com.scatt.eredbe.rries.utils.lib

import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.scatt.eredbe.rries.utils.device.Analytics
import com.scatt.eredbe.rries.utils.storage.Bucket
import com.scatt.eredbe.rries.utils.string.Ids

object AppsLib {

    fun init(context: Context, callback: (link: String)->Unit) {
        val appsflyer = AppsFlyerLib.getInstance()
        appsflyer.setMinTimeBetweenSessions(0)

        appsflyer.init(Ids.appsId(), object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                if (!Analytics.isLaunch) {
                    Analytics.isLaunch = true
                    Analytics.appsConversionDataSuccess(conversionData)
                    Analytics.appsReceivedTime()
                    // если пришел атьрибут имя компании
                    if (conversionData["campaign"] != null) {
                        // Записываем в Shared Pref
                        Bucket.campaign =
                            conversionData["campaign"].toString()
                        Analytics.namingReceived(conversionData["campaign"].toString())
                        callback(conversionData["campaign"].toString())
                    }
                }
            }

            override fun onConversionDataFail(errorMessage: String) {
                Analytics.appsConversionDataFail(errorMessage)
            }

            override fun onAppOpenAttribution(attributionData: Map<String, String>) {
                Analytics.appsAppOpenAttribution(attributionData)
            }

            override fun onAttributionFailure(errorMessage: String) {
                Analytics.appsAppAttributionFailure(errorMessage)
            }
        }, context)
        appsflyer.start(context)
    }
}