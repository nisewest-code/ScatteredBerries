package com.scatt.eredbe.rries.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.*
import androidx.activity.result.ActivityResultLauncher
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.scatt.eredbe.rries.activity.LaunchBoard
import com.scatt.eredbe.rries.databinding.ActivityLaunchBoardBinding
import com.scatt.eredbe.rries.utils.device.Analytics
import com.scatt.eredbe.rries.utils.storage.Bucket
import com.scatt.eredbe.rries.view.support.ScarretBerriesS
import com.scatt.eredbe.rries.view.support.ScatteredBeriesHelper
import com.scatt.eredbe.rries.view.support.ScatteredOreoBerries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class WbCustom(private val context: LaunchBoard, private val binding: ActivityLaunchBoardBinding, private val listener: ActivityResultLauncher<Intent>) {

    private lateinit var webView: WebView
    var chooserCallback: ValueCallback<Array<Uri>>? = null
    private var views = Stack<WebView>()
    private var mFullScreenView: View? = null
    private var mFullscreenViewCallback: WebChromeClient.CustomViewCallback? = null

    fun listenerSuccess(intent: Intent){
        chooserCallback?.let {
            intent.data?.let {
                val dataUris: Array<Uri>? = try {
                    arrayOf(Uri.parse(intent.dataString))
                } catch (e: Exception) {
                    null
                };chooserCallback!!.onReceiveValue(dataUris)
                chooserCallback = null
            }
        }
    }

    fun listenerOnFail(){
        if (chooserCallback != null) {
            chooserCallback!!.onReceiveValue(null)
            chooserCallback = null
        }
    }
    fun setFullscreen(window: Window): View {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            window.decorView.getWindowVisibleDisplayFrame(r)
            val height: Int =
                context.resources.displayMetrics.heightPixels
            val diff: Int = height - r.bottom
            if (diff != 0) {
                if (binding.container.paddingBottom !== diff) {
                    binding.container.setPadding(0, 0, 0, diff)
                }
            } else {
                if (binding.container.paddingBottom !== 0) {
                    binding.container.setPadding(0, 0, 0, 0)
                }
            }
        }
        return decorView
    }

    fun onWindowFocusChanged(window: Window) {
        setFullscreen(window)
    }


    fun init() {
        binding.progressBar.visibility = View.GONE
        context.supportActionBar?.hide()

        val view = ConstraintLayout(context)
        view.layoutParams =
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
        webView = WebView(context)
        webView.layoutParams =
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
        view.addView(webView)
        binding.root.addView(view)

        try {
            webView.setDownloadListener { url, userAgent, contentDescription, mimetype, _ ->
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                context.startActivity(i)
            }
            val fdf = ScatteredOreoBerries(webView){
                it.initSettings()
            }
            val dfdfder = ScatteredBeriesHelper(webView){
                it.initSettings{ true }
            }
            views.push(webView)
//
            CoroutineScope(Dispatchers.Main).launch {
                val lastUrl = Bucket.lastUrl
                if (lastUrl.isEmpty()){
                    val firstUrl = Bucket.startUrl
                    val wbClientMissing = ScarretBerriesS(mutableListOf(context), firstUrl)
                    webView.webChromeClient = MisisngClz(context)
                    webView.webViewClient = wbClientMissing.retrirtComms()[0][0][0]
//                    Analytics.openUrl(firstUrl)
                    webView.loadUrl(firstUrl)
                    return@launch
                }
                val wbCherryClient = ScarretBerriesS(mutableListOf(context), lastUrl)
                webView.webChromeClient = MisisngClz(context)
                webView.webViewClient = wbCherryClient.retrirtComms()[0][0][0]
//                Analytics.openUrl(lastUrl)
                webView.loadUrl(lastUrl)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


    private inner class MisisngClz(val activity: LaunchBoard) : WebChromeClient() {
        override fun onHideCustomView() {
            binding.apply {
                binding.containerScattered.removeView(mFullScreenView)
                mFullscreenViewCallback?.onCustomViewHidden()
                mFullScreenView = null
                webView.visibility = View.VISIBLE
                binding.containerScattered.visibility = View.GONE
            }
        }

        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
            binding.apply {
                webView.visibility = View.GONE
                binding.containerScattered.visibility = View.VISIBLE
                binding.containerScattered.addView(view)
                mFullScreenView = view
                mFullscreenViewCallback = callback
                binding.root.invalidate()
            }
        }

        override fun onCreateWindow(
            view: WebView,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            val secondWebView = WebView(context)
            views.peek()?.isVisible = false

            secondWebView.setDownloadListener { url, userAgent, contentDescription, mimetype, _ ->
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                context.startActivity(i)
            }
            val wbMissing = ScarretBerriesS(mutableListOf(context), "")
            secondWebView.webChromeClient = MisisngClz(context)
            secondWebView.webViewClient = wbMissing.retrirtComms()[0][0][0]
            val mpc = ScatteredOreoBerries(secondWebView){
                it.initSettings()
            }
            val fdfdd = ScatteredBeriesHelper(secondWebView){
                it.initSettings { true }
            }
            views.push(secondWebView)
            binding.root.addView(secondWebView)
            val transport = resultMsg!!.obj as WebView.WebViewTransport
            transport.webView = secondWebView
            resultMsg.sendToTarget()
            return true
        }

        override fun onCloseWindow(window: WebView?) {
            super.onCloseWindow(window)
            val secondWebView = if (!views.isEmpty()) views.pop() else null
            if (secondWebView != null && secondWebView.isVisible) {
                views.peek()?.isVisible = true
                secondWebView.isVisible = false
                binding.root.removeView(secondWebView)
                secondWebView.destroy()
            }
        }

        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            chooserCallback?.let { chooserCallback!!.onReceiveValue(null) }
            chooserCallback = filePathCallback
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            listener.launch(Intent.createChooser(intent, ""))
            return true
        }

        override fun onPermissionRequest(request: PermissionRequest?) {
            if (
                ActivityCompat.checkSelfPermission(
                    context.applicationContext,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    context.applicationContext,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                    ), 102
                )
            } else {
                request?.grant(request.resources)
            }
        }

    }


    fun onSaveInstanceState(outState: Bundle) {
        if (webView.isVisible) {
            webView.saveState(outState)
        }
    }

    private var one = 0

    fun onBackPressed(callback: ()->Unit) {
        if (views.size > 1) {

            val webz = views.peek()
            if (webz?.canGoBack() == true) {
                webz?.goBack()
            } else {
                webz?.isVisible = false
                binding.root.removeView(webz)
                webz?.destroy()
                views.pop()
                views.peek()?.isVisible = true
            }
        } else if (views.size == 1) {
            if (views.peek()?.canGoBack() == true) {
                views.peek()?.goBack()
            } else {
                callback()
            }
        } else {
            callback()
            one++
            if (one == 2){
                one = 0
                Analytics.backclickRush()
            }
        }
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        if (webView.isVisible) {
            webView.restoreState(savedInstanceState)
        }
    }

    fun onResume() {
        if (webView.isVisible) {
            CookieManager.getInstance().flush()
            webView.onResume()
        }
    }

    fun onPause() {
        if (webView.isVisible) {
            CookieManager.getInstance().flush()
            webView.onPause()
        }
    }
}