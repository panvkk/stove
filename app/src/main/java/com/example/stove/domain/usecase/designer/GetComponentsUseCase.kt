package com.example.stove.domain.usecase.designer

import com.example.stove.core.Resource
import com.example.stove.domain.model.designer.Component
import com.example.stove.domain.repository.DesignerRepository
import com.example.stove.presentation.model.ComponentUiModel
import javax.inject.Inject

class GetComponentsUseCase @Inject constructor(
    private val repository: DesignerRepository
) {
    suspend fun invoke(typeId: Int) : Resource<List<ComponentUiModel>> {
        val resource = repository.getComponents(typeId)

        return when(resource) {
            is Resource.LOADING -> Resource.LOADING()
            is Resource.SUCCESS -> Resource.SUCCESS(resource.data.map { it.toUiModel() })
            /** Добавить DomainError и обрабатывать ошибки на соответствующем слое */
            is Resource.FAILURE -> Resource.FAILURE(resource.error)
        }

    }

    private fun Component.toUiModel() = ComponentUiModel(id, name, description, isRequired, allowMultipleChoices, componentOptions)
}


