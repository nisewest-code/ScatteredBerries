package com.scatt.eredbe.rries

import android.app.Application
import com.bugsee.library.Bugsee
import com.scatt.eredbe.rries.utils.device.Analytics
import com.scatt.eredbe.rries.utils.device.DeviceUtil
import com.scatt.eredbe.rries.utils.lib.OneLib
import com.scatt.eredbe.rries.utils.storage.Bucket
import com.scatt.eredbe.rries.utils.string.Ids
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppScerretBerries: Application() {

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Default).launch {
            Bucket.init(this@AppScerretBerries)
        }
        Analytics.init(this)

        Bugsee.launch(this, Ids.bugId())

        DeviceUtil.checkDevice(
            this, callbackFail = {},
            callbackSuccess = {
                OneLib.init(this)
            }
        )

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
    }
}