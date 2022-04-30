package com.onn.krokit.domain


class SendDataUseCase (private val repository: GameRepository) {
    suspend operator fun invoke() = repository.getData()
}