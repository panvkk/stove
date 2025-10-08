package com.example.stove.data.repository

import android.util.Log
import com.example.stove.core.ApiError
import com.example.stove.core.Resource
import com.example.stove.data.dto.AddonDto
import com.example.stove.data.dto.ComponentDto
import com.example.stove.data.dto.ConfigComponentDto
import com.example.stove.data.dto.configuration.NewConfigurationDto
import com.example.stove.data.dto.OptionDto
import com.example.stove.data.dto.TypeDto
import com.example.stove.data.dto.configuration.NewConfigurationResponse
import com.example.stove.data.remote.service.AuthDesignerApiService
import com.example.stove.data.remote.service.PublicDesignerApiService
import com.example.stove.domain.model.designer.Addon
import com.example.stove.domain.model.designer.Component
import com.example.stove.domain.model.designer.ConfigComponent
import com.example.stove.domain.model.designer.ExtendedConfiguration
import com.example.stove.domain.model.designer.Option
import com.example.stove.domain.model.designer.Type
import com.example.stove.domain.repository.DesignerRepository
import com.squareup.moshi.Moshi
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class DesignerRepositoryImpl @Inject constructor(
    private val publicApiService: PublicDesignerApiService,
    private val authApiService: AuthDesignerApiService,
    private val moshi: Moshi
) : DesignerRepository {

    private val errorAdapter = moshi.adapter(ApiError::class.java)

    override suspend fun putConfiguration(configuration: NewConfigurationDto): Resource<ExtendedConfiguration> {
        return try {
            val response = authApiService.putConfiguration(configuration)
            if(response.isSuccessful) {
                Resource.SUCCESS(response.body()!!.toDomain())
            } else {
                val httpCode = response.code()
                val errorBody = response.errorBody()?.string() ?: "Unknown Error."
                val apiError = errorAdapter.fromJson(errorBody)
                Resource.FAILURE(Throwable(apiError?.message))
            }
        } catch(e: HttpException) {
            Resource.FAILURE(Throwable(e.message))
        } catch(e: IOException) {
            Resource.FAILURE(Throwable("Network error. Check your connection."))
        }
    }

    override suspend fun getTypes(): Resource<List<Type>> {
        return try {
            val remoteTypes = publicApiService.getTypes()
            Resource.SUCCESS(remoteTypes.map {it.toDomain()})
        } catch(e: HttpException) {
            Log.e("DesignerRepository",e.message ?: "Unknown Error")
            Resource.FAILURE(e)
        }
    }

    override suspend fun getComponents(typeId: Int): Resource<List<Component>> {
        return try {
            val remoteComponents = publicApiService.getComponents(typeId)
            Resource.SUCCESS(remoteComponents.map {it.toDomain()})
        } catch(e: HttpException) {
            Log.e("DesignerRepository",e.message ?: "Unknown Error")
            Resource.FAILURE(e)
        }
    }
    override suspend fun getOptions(componentId: Int): Resource<List<Option>> {
        return try {
            val remoteOptions = publicApiService.getOptions(componentId)
            Resource.SUCCESS(remoteOptions.map {it.toDomain()})
        } catch(e: HttpException) {
            Log.e("DesignerRepository",e.message ?: "Unknown Error")
            Resource.FAILURE(e)
        }
    }
    override suspend fun getAddons(): Resource<List<Addon>> {
        return try {
            val remoteTypes = publicApiService.getAddons()
            Resource.SUCCESS(remoteTypes.map {it.toDomain()})
        } catch(e: HttpException) {
            Log.e("DesignerRepository",e.message ?: "Unknown Error")
            Resource.FAILURE(e)
        }
    }

    private fun TypeDto.toDomain() = Type(id, name, description, basePrice ?: 0, imageUrl ?: "")
    private fun ComponentDto.toDomain() = Component(id, name, description, isRequired ?: false, allowMultipleChoices ?: false, componentOptions ?: false)

    private fun OptionDto.toDomain() = Option(id, name, priceModifier ?: 1, imageUrl ?: "", isDefault ?: true)

    private fun AddonDto.toDomain() = Addon(id, name, description, price ?: 0)

    private fun ConfigComponentDto.toDomain() = ConfigComponent(componentName, chosenOption.toDomain())

    private fun NewConfigurationResponse.toDomain() =
        ExtendedConfiguration(
            id = id,
            name = name,
            isTemplate = isTemplate,
            isLocked = isLocked,
            createdAt = createdAt,
            totalPrice = totalPrice,
            stoveType = stoveType.toDomain(),
            components = components.map { component -> component.toDomain() },
            addons = addons.map { addon -> addon.toDomain() }
        )
}