package com.example.stove.presentation.ui.screens.designer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import com.example.stove.R
import com.example.stove.core.Resource
import com.example.stove.presentation.ui.component.DesignerButtonsRow
import com.example.stove.presentation.ui.component.DesignerOptionCard
import com.example.stove.presentation.ui.theme.StoveTheme
import com.example.stove.presentation.viewmodel.DesignerUiState
import com.example.stove.presentation.viewmodel.DesignerViewModel

@Composable
fun DesignerComponentScreen(
    viewModel: DesignerViewModel,
    backBehavior: () -> Unit,
    nextBehavior: () -> Unit
) {
    val draft by viewModel.draft.collectAsState()
    val uiState by viewModel.designerUiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val currentState = uiState
    if(showDialog && currentState is DesignerUiState.COMPONENT) {
        OptionDialog(
            onCloseRequest = {
                showDialog = false
            },
            currentState = currentState,
            viewModel = viewModel
        )
    }

    Column(
        modifier = Modifier
            .padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large)
        )
    ) {
        Text(
            text = stringResource(R.string.title_component),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(
                top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_small)
            )
        )
        if(currentState is DesignerUiState.COMPONENT) {
            when(currentState.components) {
                is Resource.SUCCESS -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(dimensionResource(R.dimen.cell_size)),
                    ) {
                        items(items = currentState.components.data, key = { component -> component.id }) { component ->
                            val componentId = component.id

                            DesignerOptionCard(
                                title = component.name,
                                imageUri = "".toUri(),
                                isSelected = draft.componentOptions.containsKey(componentId),
                                onClickBehavior = {
                                    viewModel.updateComponent(componentId, component.name)
                                    viewModel.loadOptions()
                                    showDialog = true
                                }
                            )
                        }
                    }
                }

                is Resource.FAILURE -> {
                    Text(
                        text = "Ошибка загрузки: ${currentState.components.error.message}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(
                            top = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_small)
                        )
                    )
                }

                is Resource.LOADING -> {
                    Text(
                        text = "Загрузка...",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(
                            top = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_small)
                        )
                    )
                }
            }
        }
        DesignerButtonsRow(
            backBehavior = { backBehavior() },
            nextBehavior = { nextBehavior() }
        )
    }
}

@Composable
fun OptionDialog(
    onCloseRequest: () -> Unit,
    currentState: DesignerUiState.COMPONENT,
    viewModel: DesignerViewModel
) {
    var selectedOption by remember { mutableStateOf(Pair(-1, "")) }

    Dialog(onDismissRequest = onCloseRequest) {
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_large))
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.8f)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            ) {
                when (currentState.options) {
                    is Resource.SUCCESS -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(dimensionResource(R.dimen.cell_size)),
                        ) {
                            items(
                                items = currentState.options.data,
                                key = { option -> option.id }
                            ) { option ->
                                val optionId = option.id

                                DesignerOptionCard(
                                    title = option.name,
                                    imageUri = option.imageUrl.toUri(),
                                    isSelected = selectedOption.first == optionId,
                                    onClickBehavior = {
                                        selectedOption = Pair(optionId, option.name)
                                    }
                                )
                            }
                        }
                    }

                    is Resource.FAILURE -> {
                        Text(
                            text = "Ошибка загрузки: ${currentState.options.error.message}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(
                                top = dimensionResource(R.dimen.padding_medium),
                                bottom = dimensionResource(R.dimen.padding_small)
                            )
                        )
                    }

                    is Resource.LOADING -> {
                        Text(
                            text = "Загрузка...",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(
                                top = dimensionResource(R.dimen.padding_medium),
                                bottom = dimensionResource(R.dimen.padding_small)
                            )
                        )
                    }
                }
                DesignerButtonsRow(
                    backBehavior = { onCloseRequest() },
                    nextBehavior = {
                        onCloseRequest()
                        if (selectedOption != Pair(-1, "")) {
                            viewModel.updateOption(selectedOption.first, selectedOption.second)

                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DesignerMaterialScreenPreview() {
    StoveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
//              DesignerMaterialScreen()
        }
    }
}
