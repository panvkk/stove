package com.example.stove.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stove.core.Resource
import com.example.stove.domain.model.designer.Configuration
import com.example.stove.domain.usecase.designer.GetAddonsUseCase
import com.example.stove.domain.usecase.designer.GetComponentsUseCase
import com.example.stove.domain.usecase.designer.GetOptionsUseCase
import com.example.stove.domain.usecase.designer.GetTypesUseCase
import com.example.stove.domain.usecase.designer.PutConfigurationUseCase
import com.example.stove.presentation.model.AddonUiModel
import com.example.stove.presentation.model.ComponentOption
import com.example.stove.presentation.model.ComponentUiModel
import com.example.stove.presentation.model.ConfigDraft
import com.example.stove.presentation.model.ExtendedConfigUiModel
import com.example.stove.presentation.model.OptionUiModel
import com.example.stove.presentation.model.TypeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DesignerUiState {
    data object ENTRY : DesignerUiState()
    data class TYPE(val types: Resource<List<TypeUiModel>>) : DesignerUiState()
    data class COMPONENT(
        val components: Resource<List<ComponentUiModel>>,
        val options: Resource<List<OptionUiModel>> = Resource.LOADING()
    ) : DesignerUiState()
    data class ADDON(val addons: Resource<List<AddonUiModel>>) : DesignerUiState()

    data object NAME : DesignerUiState()

    data class SUMMARY(val extendedConfig: Resource<ExtendedConfigUiModel>) : DesignerUiState()
}

sealed interface DesignerNavigationEvents {
    data object ToStart : DesignerNavigationEvents
}

@HiltViewModel
class DesignerViewModel @Inject constructor(
    private val getTypesCase: GetTypesUseCase,
    private val getComponentsCase: GetComponentsUseCase,
    private val getOptionsCase: GetOptionsUseCase,
    private val getAddonsCase: GetAddonsUseCase,
    private val putConfigurationCase: PutConfigurationUseCase,
    private val snackbarEventSource: SnackbarEventSource
): ViewModel() {
    private val _navigationEvent = Channel<DesignerNavigationEvents>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _designerUiState = MutableStateFlow<DesignerUiState>(DesignerUiState.ENTRY)
    val designerUiState: StateFlow<DesignerUiState> = _designerUiState

    private val _draft = MutableStateFlow(ConfigDraft())
    val draft: StateFlow<ConfigDraft> = _draft

    fun putConfiguration() {
        viewModelScope.launch {
            _designerUiState.value = DesignerUiState.SUMMARY(Resource.LOADING())
            val response = putConfigurationCase.invoke(_draft.value.toDomain())
            if(response is Resource.SUCCESS) {
                if(_designerUiState.value is DesignerUiState.SUMMARY) {
                    _designerUiState.value =
                        DesignerUiState.SUMMARY(Resource.SUCCESS(response.data))
                }
                snackbarEventSource.postSnackbar("Конфигурация успешно добавлена.")
            } else if(response is Resource.FAILURE){
                _designerUiState.value = DesignerUiState.SUMMARY(Resource.FAILURE(response.error))
                Log.e("DesignerViewModel", "Error adding configuration.")
                snackbarEventSource.postSnackbar("Ошибка при добавлении конфигурации.")
                Log.e("DesignerViewModel", "Error while adding configuration: " + (response.error.message ?: "Unknown error."))
            }
        }
    }

    fun loadTypes() {
        viewModelScope.launch {
            _designerUiState.value = DesignerUiState.TYPE(Resource.LOADING())
            _designerUiState.value = DesignerUiState.TYPE(
                getTypesCase.invoke()
            )
        }
    }


    fun loadComponents() {
        viewModelScope.launch {
            _designerUiState.value = DesignerUiState.COMPONENT(Resource.LOADING())
            val type = _draft.value.type
            if(type != null) {
                _designerUiState.value = DesignerUiState.COMPONENT(
                    getComponentsCase.invoke(type.first))
            } else {
                _designerUiState.value = DesignerUiState.COMPONENT(
                    Resource.FAILURE(Throwable("Type id is null."))
                )
            }

        }
    }

    fun loadOptions() {
        viewModelScope.launch {
            if(_designerUiState.value is DesignerUiState.COMPONENT) {
                val component = _draft.value.selectedComponent
                if(component != null) {
                    _designerUiState.value = DesignerUiState.COMPONENT(
                        components = (_designerUiState.value as DesignerUiState.COMPONENT).components,
                        options = getOptionsCase.invoke(componentId = component.first)
                    )
                } else {
                    _designerUiState.value = DesignerUiState.COMPONENT(
                        components = (_designerUiState.value as DesignerUiState.COMPONENT).components,
                        options = Resource.FAILURE(Throwable("Component id is null."))
                    )
                }
            }

        }
    }

    fun loadAddons() {
        viewModelScope.launch {
            _designerUiState.value = DesignerUiState.ADDON(Resource.LOADING())
            _designerUiState.value = DesignerUiState.ADDON(
                getAddonsCase.invoke()
            )
        }
    }

    fun updateType(typeId: Int, typeName: String) {
        _draft.update {
            draft -> draft.copy(type = Pair(typeId, typeName))
        }
    }

    fun updateComponent(componentId: Int, componentName: String) {
        _draft.update {
            draft -> draft.copy(selectedComponent = Pair(componentId, componentName))
        }
    }

    fun updateOption(optionId: Int, optionName: String) {
        val component = _draft.value.selectedComponent
        if(component == null) { return }
        val newComponentOptions = _draft.value.componentOptions +
                (component.first to ComponentOption(component.second, optionId, optionName))
        _draft.update { draft ->
            draft.copy(componentOptions = newComponentOptions)
        }
    }

    fun updateAddon(addonId: Int, addonName: String) {
        _draft.update { draft ->
            draft.copy(
                addons = if(_draft.value.addons.containsKey(addonId)) {
                        _draft.value.addons.minus(addonId)
                    } else {
                        _draft.value.addons.plus(addonId to addonName)
                    }
            )
        }
    }

    fun updateDraftName(draftName: String) {
        _draft.update { draft ->
            draft.copy(draftName = draftName)
        }
    }

    fun ConfigDraft.toDomain() = Configuration(
        type?.first,
        componentOptions.keys.toList(),
        addons.keys.toList(),
        draftName
        )

}