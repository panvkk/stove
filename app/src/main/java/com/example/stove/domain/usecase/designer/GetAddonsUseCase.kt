package com.example.stove.domain.usecase.designer

import com.example.stove.core.Resource
import com.example.stove.domain.model.designer.Addon
import com.example.stove.domain.repository.DesignerRepository
import com.example.stove.presentation.model.AddonUiModel
import javax.inject.Inject

class GetAddonsUseCase @Inject constructor(
    private val repository: DesignerRepository
) {
    suspend fun invoke() : Resource<List<AddonUiModel>> {
        val resource = repository.getAddons()

        return when(resource) {
            is Resource.LOADING -> Resource.LOADING()
            is Resource.SUCCESS -> Resource.SUCCESS(resource.data.map { it.toUiModel() })
            /** Добавить DomainError и обрабатывать ошибки на соответствующем слое */
            is Resource.FAILURE -> Resource.FAILURE(resource.error)
        }

    }

    private fun Addon.toUiModel() = AddonUiModel(id, name, description, price)
}