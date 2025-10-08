package com.example.stove.domain.repository

import com.example.stove.core.Resource
import com.example.stove.data.dto.configuration.NewConfigurationDto
import com.example.stove.domain.model.designer.Addon
import com.example.stove.domain.model.designer.Component
import com.example.stove.domain.model.designer.ExtendedConfiguration
import com.example.stove.domain.model.designer.Option
import com.example.stove.domain.model.designer.Type

interface DesignerRepository {
    suspend fun getTypes() : Resource<List<Type>>

    suspend fun getComponents(typeId: Int) : Resource<List<Component>>

    suspend fun getOptions(componentId: Int) : Resource<List<Option>>

    suspend fun getAddons() : Resource<List<Addon>>

    suspend fun putConfiguration(configuration: NewConfigurationDto) : Resource<ExtendedConfiguration>
}