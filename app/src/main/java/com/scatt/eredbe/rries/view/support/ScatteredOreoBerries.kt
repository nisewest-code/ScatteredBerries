package com.scatt.eredbe.rries.view.support

import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView

class ScatteredOreoBerries(val webView: WebView, callback: (item: ScatteredOreoBerries)->Unit) {

    init {
        webView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        callback(this)
    }
    fun initSettings() {
        CookieManager.getInstance().setAcceptThirdPartyCookies( webView, true)
        CookieManager.getInstance().setAcceptCookie(true)
        webView.setOnTouchListener { _, _ ->
            CookieManager.getInstance().flush()
            false
        }
    }
}