package com.onn.krokit.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.onesignal.OneSignal

class MyApps: Application() {
    private val oneSignalKey by lazy { "c8e6fc67-04da-4eae-8ead-3129c6e0e3f3" }
    private val keyDevAppsflyer by lazy { "cP477pXCQNUexrPcf8gs46" }
    private var getData = false
    companion object{
        var liveDataAppsFlyer = MutableLiveData<MutableMap<String, Any>>()
    }

    override fun onCreate() {
        super.onCreate()
        AppsFlyerLib.getInstance().init(keyDevAppsflyer, appsFlyerConversion(), this)
        AppsFlyerLib.getInstance().start(this)
        OneSignal.initWithContext(this);
        OneSignal.setAppId(oneSignalKey)
    }

    private fun appsFlyerConversion(): AppsFlyerConversionListener {

        return object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                if (!getData){
                    data?.let {
                        liveDataAppsFlyer.postValue(it)
                    }
                    getData = true
                }
            }

            override fun onConversionDataFail(error: String?) {
                Log.d("error", "$error")
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                Log.d("data", "$data")
            }

            override fun onAttributionFailure(error: String?) {
                Log.d("error", "$error")
            }
        }
    }
}