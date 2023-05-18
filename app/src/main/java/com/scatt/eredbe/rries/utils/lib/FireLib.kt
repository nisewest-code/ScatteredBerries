package com.scatt.eredbe.rries.utils.lib

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.scatt.eredbe.rries.utils.device.Analytics
import com.scatt.eredbe.rries.utils.storage.Bucket
import com.scatt.eredbe.rries.utils.string.Ids
import com.scatt.eredbe.rries.utils.string.Linked
import com.scatt.eredbe.rries.utils.string.StrHelp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object FireLib {
    fun init(context: Context, callbackSuccess: (link: String)->Unit, callbackFail: ()->Unit){
        val db = Firebase.firestore
        val docRef = db.collection("bucket").document(StrHelp.getIdFire(context))
        docRef.get()
            .addOnSuccessListener { document ->
                Analytics.backendReceivedTime()
                // Если имя компании и диплинк не пришли
                val nameRef = "url"
                if (document != null) {
                    var urlFirebase = document.getString(nameRef)
                    if (urlFirebase != null) {
                        if (urlFirebase.isEmpty()) {
                            callbackFail()
                        } else {
                            Analytics.backendUrl(urlFirebase)
                            urlFirebase = if (Linked.link.isNotEmpty()) {
                                StrHelp.getNonOrganic(context, Linked.link, urlFirebase, Linked.advertisingId, Ids.appsId())
                            } else {
                                StrHelp.getOrganic(context, urlFirebase, Linked.advertisingId, Ids.appsId())

                            }
                            CoroutineScope(Dispatchers.Main).launch {
                                Bucket.startUrl = urlFirebase
                            }
                            Analytics.logFirstUrl(urlFirebase)
                            callbackSuccess(urlFirebase)
                        }
                    } else {
                        callbackFail()
                    }
                } else {
                    callbackFail()
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callbackFail()
            }
    }
}