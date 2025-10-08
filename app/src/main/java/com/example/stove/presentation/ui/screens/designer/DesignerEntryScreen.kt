package com.example.stove.presentation.ui.screens.designer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stove.R
import com.example.stove.presentation.navigation.NavigationDestination
import com.example.stove.presentation.ui.component.CustomButton
import com.example.stove.presentation.ui.theme.StoveTheme
import com.example.stove.presentation.viewmodel.DesignerViewModel

object DesignerEntryDestination : NavigationDestination {
    override val route: String = "DesignerEntry"
    override val titleRes: Int = R.string.title_constructor
}

@Composable
fun DesignerEntryScreen(
    startDesigner: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large)
        )
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.welcome_message),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
        Text(
            text = stringResource(R.string.subtitle_create),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        )
        CustomButton(
            labelId = R.string.button_create_new,
            textStyle = MaterialTheme.typography.labelLarge,
            isActiveButton = true,
            onClickBehavior = { startDesigner() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_medium)
                )
        )
        CustomButton(
            labelId = R.string.button_choose_favorite,
            textStyle = MaterialTheme.typography.labelLarge,
            isActiveButton = false,
            onClickBehavior = { TODO() },
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
fun DesignerEntryScreenPreview() {
    StoveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
//            DesignerEntryScreen()
        }
    }
}