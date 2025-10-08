package com.example.stove.presentation.ui.screens.designer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.example.stove.R
import com.example.stove.core.Resource
import com.example.stove.presentation.navigation.NavigationDestination
import com.example.stove.presentation.ui.component.DesignerButtonsRow
import com.example.stove.presentation.ui.component.DesignerOptionCard
import com.example.stove.presentation.ui.theme.StoveTheme
import com.example.stove.presentation.viewmodel.DesignerUiState
import com.example.stove.presentation.viewmodel.DesignerViewModel

object DesignerTypeDestination : NavigationDestination {
    override val route: String = "DesignerType"
    override val titleRes: Int = R.string.title_constructor
}

@Composable
fun DesignerTypeScreen(
    viewModel: DesignerViewModel,
    backBehavior: () -> Unit,
    nextBehavior: () -> Unit
) {
    val draft by viewModel.draft.collectAsState()
    val uiState by viewModel.designerUiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large)
        )
    ) {
        Text(
            text = stringResource(R.string.title_type),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(
                top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_small)
            )
        )
        val currentState = uiState
        if(currentState is DesignerUiState.TYPE) {
            when(currentState.types) {
                is Resource.SUCCESS -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(dimensionResource(R.dimen.cell_size)),
                    ) {
                        items(items = currentState.types.data, key = { type -> type.id}) { type ->
                            val typeId = type.id

                            DesignerOptionCard(
                                title = type.name,
                                imageUri = type.imageUrl.toUri(),
                                isSelected = draft.type?.first == typeId,
                                onClickBehavior = {
                                    viewModel.updateType(typeId, type.name)
                                }
                            )
                        }
                    }
                }
                is Resource.FAILURE -> {
                    Text(
                        text = "Ошибка загрузки: ${currentState.types.error.message}",
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

@Preview
@Composable
fun DesignerTypeScreenPreview() {
    StoveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
//            DesignerTypeScreen()
        }
    }
}
