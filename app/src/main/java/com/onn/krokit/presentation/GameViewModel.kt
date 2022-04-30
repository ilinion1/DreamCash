package com.onn.krokit.presentation


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onn.krokit.R
import com.onn.krokit.data.Contain
import com.onn.krokit.data.GameRepositoryImpl
import com.onn.krokit.data.MyApiFactory
import com.onn.krokit.domain.SendDataUseCase

class GameViewModel: ViewModel() {
    private val api = MyApiFactory.create()
    private val repository = GameRepositoryImpl(api)
    private val sendDataUseCase = SendDataUseCase(repository)

    var setLightTheme = MutableLiveData(true)
    val imageList1 = listOf(R.drawable.a, R.drawable.champagne, R.drawable.clock, R.drawable.handbag,
        R.drawable.j, R.drawable.k, R.drawable.q, R.drawable.ring, R.drawable.travel)
    var listResult = arrayListOf<Int>()
    var imagePosition = 0
    var countImage = 0

    suspend fun getData(): Contain {
        return sendDataUseCase()
    }
}