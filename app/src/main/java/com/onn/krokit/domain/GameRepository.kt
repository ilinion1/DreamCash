package com.onn.krokit.domain

import com.onn.krokit.data.Contain


interface GameRepository {
    suspend fun getData(): Contain
}