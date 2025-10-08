package com.example.stove.presentation.ui.screens.designer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stove.R
import com.example.stove.core.Resource
import com.example.stove.presentation.navigation.NavigationDestination
import com.example.stove.presentation.ui.component.CustomButton
import com.example.stove.presentation.ui.theme.StoveTheme
import com.example.stove.presentation.viewmodel.DesignerUiState
import com.example.stove.presentation.viewmodel.DesignerViewModel

object DesignerSummaryDestination : NavigationDestination {
    override val route: String = "DesignerSummary"
    override val titleRes: Int = R.string.title_constructor
}

@Composable
fun DesignerSummaryScreen(
    viewModel: DesignerViewModel
) {
    val uiState by viewModel.designerUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensionResource(R.dimen.padding_large))
    ) {
        Text(
            text = "Итог",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(16.dp))

        if((uiState as DesignerUiState.SUMMARY).extendedConfig is Resource.SUCCESS) {
            val extendedConfig = ((uiState as DesignerUiState.SUMMARY).extendedConfig as Resource.SUCCESS).data
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = extendedConfig.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Тип печи: ${extendedConfig.stoveType.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Шаблон: ${if (extendedConfig.isTemplate) "Да" else "Нет"} | Заблокировано: ${if (extendedConfig.isLocked) "Да" else "Нет"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Выбранные компоненты",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            extendedConfig.components.forEach { component ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${component.componentName}:",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = component.chosenOption.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "+${component.chosenOption.priceModifier} ₽",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), thickness = 0.5.dp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (extendedConfig.addons.isNotEmpty()) {
                Text(
                    text = "Дополнительные услуги",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                extendedConfig.addons.forEach { addon ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = addon.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = addon.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }

                        Text(
                            text = "+${addon.price} ₽",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), thickness = 0.5.dp)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ИТОГО:",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = "${extendedConfig.totalPrice} ₽",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else if((uiState as DesignerUiState.SUMMARY).extendedConfig is Resource.FAILURE) {
            val errorMessage = ((uiState as DesignerUiState.SUMMARY).extendedConfig as Resource.FAILURE).error.message
            Text(
                text = "Ошибка: " + (errorMessage ?: "Неизвестная ошибка."),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            Text(
                text = "Загрузка...",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
        CustomButton(
            labelId = R.string.button_order,
            textStyle = MaterialTheme.typography.labelMedium,
            isActiveButton = true,
            onClickBehavior = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.padding_large),
                    bottom = dimensionResource(R.dimen.padding_medium)
                )
        )
        CustomButton(
            labelId = R.string.button_add_to_favourites,
            textStyle = MaterialTheme.typography.labelLarge,
            isActiveButton = false,
            onClickBehavior = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = dimensionResource(R.dimen.padding_medium)
                )
        )
    }
}

@Preview
@Composable
fun DesignerSummaryScreenPreview() {
    StoveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DesignerSummaryScreen(
//                backBehavior = {},
//                nextBehavior = {},
                viewModel = viewModel()
            )
        }
    }
}
