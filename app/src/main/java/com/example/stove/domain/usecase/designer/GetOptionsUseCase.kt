package com.example.stove.domain.usecase.designer

import com.example.stove.core.Resource
import com.example.stove.domain.model.designer.Option
import com.example.stove.domain.repository.DesignerRepository
import com.example.stove.presentation.model.OptionUiModel
import javax.inject.Inject

class GetOptionsUseCase @Inject constructor(
        private val repository: DesignerRepository
    )
{
    suspend fun invoke(componentId: Int) : Resource<List<OptionUiModel>> {
        val resource = repository.getOptions(componentId)

        return when(resource) {
            is Resource.LOADING -> Resource.LOADING()
            is Resource.SUCCESS -> Resource.SUCCESS(resource.data.map { it.toUiModel() })
            /** Добавить DomainError и обрабатывать ошибки на соответствующем слое */
            is Resource.FAILURE -> Resource.FAILURE(resource.error)
        }

    }

    private fun Option.toUiModel() = OptionUiModel(id, name, priceModifier, imageUrl, isDefault)
}