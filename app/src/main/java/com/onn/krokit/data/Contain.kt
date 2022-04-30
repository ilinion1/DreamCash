package com.onn.krokit.data

import com.google.gson.annotations.SerializedName

data class Contain(@SerializedName("users" ) var users : ArrayList<Users> = arrayListOf())
