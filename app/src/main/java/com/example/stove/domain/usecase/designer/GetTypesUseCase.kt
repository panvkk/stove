package com.example.stove.domain.usecase.designer

import com.example.stove.core.Resource
import com.example.stove.domain.model.designer.Type
import com.example.stove.domain.repository.DesignerRepository
import com.example.stove.presentation.model.TypeUiModel
import javax.inject.Inject

class GetTypesUseCase @Inject constructor(
    private val repository: DesignerRepository
) {
    suspend fun invoke() : Resource<List<TypeUiModel>> {
        val resource = repository.getTypes()

        return when(resource) {
            is Resource.LOADING -> Resource.LOADING()
            is Resource.SUCCESS -> Resource.SUCCESS(resource.data.map { it.toUiModel() })
            /** Добавить DomainError и обрабатывать ошибки на соответствующем слое */
            is Resource.FAILURE -> Resource.FAILURE(resource.error)
        }

    }

    private fun Type.toUiModel() = TypeUiModel(id, name, description, basePrice, imageUrl)
}


