package com.onn.krokit.data

import com.onn.krokit.domain.GameRepository

class GameRepositoryImpl (private val apiService: MyApiService): GameRepository {

    /**
     * Отправляю данные на сервер
     */
    override suspend fun getData(): Contain {
        return apiService.getDataServer()
    }
}