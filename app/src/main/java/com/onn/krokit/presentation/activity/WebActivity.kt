package com.onn.krokit.presentation.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.onn.krokit.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebBinding
    private var fileData: ValueCallback<Uri>? = null
    private var filePath: ValueCallback<Array<Uri>>? = null
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var link: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getStringExtra("link")?.let { link = it }
        startWebView()
        startResultLauncher()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun startWebView() = with(binding) {
        webId.loadUrl(link)
        webId.settings.javaScriptEnabled = true
        webId.settings.domStorageEnabled = true
        webId.settings.loadWithOverviewMode = true
        webId.clearCache(false)
        webId.settings.cacheMode = WebSettings.LOAD_DEFAULT
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webId, true)
        webId.webChromeClient = ChromeClient()
        webId.webViewClient = WebViewClient()
    }



    private inner class ChromeClient : WebChromeClient() {
        override fun onShowFileChooser(
            webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            filePath = filePathCallback
            launchResult()
            return true
        }
    }


    private fun launchResult() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "image/*"
        startForResult.launch(i)
    }

    private fun startResultLauncher() {
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (fileData == null && filePath == null) return@registerForActivityResult
                val resultFileData: Uri?
                val resultsFilePath: Array<Uri>?
                if (result.data == null) {
                    resultFileData = null
                    resultsFilePath = null
                } else {
                    resultFileData = result.data?.data
                    resultsFilePath = arrayOf(Uri.parse(result.data?.dataString))
                }
                fileData?.onReceiveValue(resultFileData)
                filePath?.onReceiveValue(resultsFilePath)
            }
    }

    override fun onBackPressed() {
        if (binding.webId.canGoBack()) {
            binding.webId.goBack()
        } else {
            return
        }
    }
}