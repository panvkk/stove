package com.example.stove.domain.usecase.designer

import com.example.stove.core.Resource
import com.example.stove.data.dto.AddonIdDto
import com.example.stove.data.dto.configuration.NewConfigurationDto
import com.example.stove.data.dto.OptionIdDto
import com.example.stove.domain.model.designer.Addon
import com.example.stove.domain.model.designer.ConfigComponent
import com.example.stove.domain.model.designer.Configuration
import com.example.stove.domain.model.designer.ExtendedConfiguration
import com.example.stove.domain.model.designer.Option
import com.example.stove.domain.model.designer.Type
import com.example.stove.domain.repository.DesignerRepository
import com.example.stove.presentation.model.AddonUiModel
import com.example.stove.presentation.model.ConfigComponentUiModel
import com.example.stove.presentation.model.ExtendedConfigUiModel
import com.example.stove.presentation.model.OptionUiModel
import com.example.stove.presentation.model.TypeUiModel
import javax.inject.Inject

class PutConfigurationUseCase @Inject constructor(
    private val designerRepository: DesignerRepository
) {
    suspend fun invoke(configuration: Configuration) : Resource<ExtendedConfigUiModel> {
        return try {
            val result = designerRepository.putConfiguration(configuration.toData())
            when (result) {
                is Resource.SUCCESS -> Resource.SUCCESS(result.data.toUiModel())
                is Resource.FAILURE -> Resource.FAILURE(result.error)
                else -> { Resource.LOADING()  }
            }
        } catch (e: Throwable) {
            Resource.FAILURE(e)
        }
    }

    private fun Configuration.toData() : NewConfigurationDto {
        if(typeId != null) {
            return NewConfigurationDto(
                typeId,
                draftName,
                optionIds.map { it -> OptionIdDto(it) },
                addonIds.map { it -> AddonIdDto(it) }
            )
        } else {
            throw(Throwable("Error with data: type_id = null"))
        }
    }

    private fun ConfigComponent.toUiModel() =
        ConfigComponentUiModel(componentName, chosenOption = chosenOption.toUiModel())
    private fun Addon.toUiModel() = AddonUiModel(id, name, description, price)
    private fun Type.toUiModel() = TypeUiModel(id, name, description, basePrice, imageUrl)
    private fun Option.toUiModel() = OptionUiModel(id, name, priceModifier, imageUrl, isDefault)

    private fun ExtendedConfiguration.toUiModel() =
        ExtendedConfigUiModel(
            id, name, isTemplate, isLocked, createdAt, totalPrice,
            stoveType.toUiModel(), components.map { component -> component.toUiModel()},
            addons.map { addon -> addon.toUiModel()}
        )
}