package com.onn.krokit.presentation.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.appsflyer.AppsFlyerLib
import com.bumptech.glide.Glide
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onn.krokit.R
import com.onn.krokit.data.MyApiFactory
import com.onn.krokit.databinding.FragmentSplashBinding
import com.onn.krokit.presentation.GameViewModel
import com.onn.krokit.presentation.MyApps
import com.onn.krokit.presentation.activity.WebActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    lateinit var binding: FragmentSplashBinding
    private var isDef = false
    private var mainLink: String? = null
    private val viewModel by lazy{ ViewModelProvider(requireActivity())[GameViewModel::class.java] }

    private var cloacaLink: String? = null
    private var googleId: String? = null
    var appsFlyerUserId: String? = null
    private val bundle = "com.onn.krokit"
    private val facebookToken by lazy { "3NiEhyOjflna1Y4B1bF7F-4wm14" }
    private val appsDevKey by lazy { "cP477pXCQNUexrPcf8gs46" }
    private val facebookAppId by lazy { "2861269650848200" }
    private var subAll = listOf<String?>(null, null, null, null)

    private var campaign: String? = null
    private var deepLink: String? = null


    private var mediaSource: String? = null
    private var afStatus: String? = null
    private var afChannel: String? = null
    private var isFirstLaunch: String? = null


    private val user by lazy { requireActivity().getSharedPreferences("hasVisited", Context.MODE_PRIVATE)
    }
    private val visited by lazy { user.getBoolean("hasVisited", true) }


    private val link by lazy { requireActivity().getSharedPreferences("link", Context.MODE_PRIVATE)
    }
    private val haveLink by lazy { link.getString("link", "") }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startWork()
    }


    /**
     * ???????????????? ?????????????????????????? ?? ???????????????? ?????????? ????????
     */
    private fun startWork() {

        if (visited) {
            lifecycleScope.launch(Dispatchers.IO) {
                getDataServer()
                getGoogleID()
                startInitialFb()
                lifecycleScope.launch(Dispatchers.Main) {
                    getAppsFlyerParams()
                }
            }
            user.edit().putBoolean("hasVisited", false).apply()
        } else {
            if (haveLink.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_splashFragment_to_menuFragment)
            } else {
                Intent(requireActivity(), WebActivity::class.java).apply {
                    putExtra("link", haveLink)
                    startActivity(this)
                }
            }
        }
    }


    private fun startInitialFb() {
        FacebookSdk.setAutoInitEnabled(true)
        FacebookSdk.fullyInitialize()
        AppLinkData.fetchDeferredAppLinkData(
            requireActivity()
        ) {
            deepLink = it?.targetUri.toString()
            deepLink?.let { deepString ->
                Log.d("test2", "$deepString")
                val arrayDeepLink = deepString.split("//")
                subAll = arrayDeepLink[1].split("_")
            }
        }
    }

    /**
     * ?????????????? id google
     */
    private fun getGoogleID() {
        val googleId = AdvertisingIdClient.getAdvertisingIdInfo(requireContext())
        this.googleId = googleId.id.toString()
    }


    /**
     * ?????????????? ?????????????????? ?? AppsFlyer
     */
    private fun getAppsFlyerParams() {
        appsFlyerUserId = AppsFlyerLib.getInstance().getAppsFlyerUID(requireActivity())
        MyApps.liveDataAppsFlyer.observe(requireActivity()) {
            for (inform in it) {
                when (inform.key) {
                    "af_status" -> {
                        afStatus = inform.value.toString()
                    }
                    "campaign" -> {
                        campaign = inform.value.toString()
                        campaign?.let { it1 -> subAll = it1.split("_") }
                    }
                    "media_source" -> {
                        mediaSource = inform.value.toString()
                    }
                    "is_first_launch" -> {
                        isFirstLaunch = inform.value.toString()
                    }
                    "af_channel" -> {
                        afChannel = inform.value.toString()
                    }
                }
            }
            nextScreen()
        }
    }

    /**
     * ?????????????? ????????????
     */
    private fun collectingLink() {
        mainLink = cloacaLink + "media_source=$mediaSource" + "&google_adid=$googleId" +
                "&af_userid=$appsFlyerUserId" + "&bundle=$bundle" + "&fb_at=$facebookToken" +
                "&dev_key=$appsDevKey" + "&app_id=$facebookAppId" + "&media_source=$mediaSource" +
                "&af_status=$afStatus" + "&af_channel=$afChannel" + "&campaign=$campaign" +
                "&is_first_launch=$isFirstLaunch" + "&sub1=${subAll[0]}" + "&sub2=${subAll[1]}" +
                "&sub3=${subAll[2]}" + "&sub4=${subAll[3]}"
        Log.d("test3", "$mainLink")
    }

    /**
     * ?????????????? ???????????? ?? ??????????????
     */
    private fun getDataServer() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                viewModel.getData().users.forEach {
                    Log.d("test3", "$it")
                    isDef = it.isdef.toBoolean()
                    cloacaLink = it.linka.toString()
                }
            } catch (e: Exception) {
                lifecycleScope.launch {
                    Toast.makeText(context, "No Internet!", Toast.LENGTH_LONG).show()
                }
                Log.d("errorGetData", "$e")
            }
        }
    }

    /**
     * ???????????????? ?????? ???????? ?????? ????????????
     */
    private fun nextScreen() {
        collectingLink() //???????????????? ????????????
        if (subAll[1] == "test2") {
            if (afStatus == null || !isDef && afStatus == "Organic" && subAll[1] == null) {
                findNavController().navigate(R.id.action_splashFragment_to_menuFragment)
            }
            if (isDef && afStatus == "Organic" || subAll[1] != null) {
                Intent(requireActivity(), WebActivity::class.java).apply {
                    link.edit().putString("link", "$mainLink").apply()
                    putExtra("link", mainLink)
                    startActivity(this)
                }
            }
        } else {
            if (afStatus == null || !isDef && afStatus == "Organic") {
                findNavController().navigate(R.id.action_splashFragment_to_menuFragment)
            }
            if (isDef && afStatus == "Organic" || subAll[1] != null) {
                Intent(requireActivity(), WebActivity::class.java).apply {
                    link.edit().putString("link", "$mainLink").apply()
                    putExtra("link", mainLink)
                    startActivity(this)
                }
            }
        }
    }
}