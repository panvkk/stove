package com.example.stove.presentation.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stove.R
import com.example.stove.presentation.navigation.NavigationDestination
import com.example.stove.presentation.ui.component.CustomButton
import com.example.stove.presentation.ui.component.IconWithBackground
import com.example.stove.presentation.ui.theme.StoveTheme


object HomeDestination : NavigationDestination {
    override val route: String = "Home"
    override val titleRes: Int = R.string.title_stoves
}

/**
 * Разобраться со Spacer()
 * */
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.stove),
            contentDescription = "Кирпичная печь",
            modifier = Modifier
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_large),
                    end = dimensionResource(R.dimen.padding_large)
                )
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.title_about_us),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.text_about_us),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Раздел "Наши услуги"
            Text(
                text = stringResource(R.string.title_services),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.padding_large),
                    bottom = dimensionResource(R.dimen.padding_large)
                )
            ) {
                Row(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.construction_of_stoves_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = dimensionResource(R.dimen.padding_medium))
                            .size(dimensionResource(R.dimen.icon_size)),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = stringResource(R.string.service_construction),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Row(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.repair_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = dimensionResource(R.dimen.padding_medium))
                            .size(dimensionResource(R.dimen.icon_size)),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = stringResource(R.string.service_repair),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Row(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.flame_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = dimensionResource(R.dimen.padding_medium))
                            .size(dimensionResource(R.dimen.icon_size)),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = stringResource(R.string.service_equipment),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Кнопки действий
            CustomButton(
                labelId = R.string.button_order,
                isActiveButton = true,
                textStyle = MaterialTheme.typography.labelMedium,
                onClickBehavior = { TODO() },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(
                labelId = R.string.button_view_works,
                isActiveButton = false,
                textStyle = MaterialTheme.typography.labelMedium,
                onClickBehavior = { TODO() },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.title_contacts),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.phone_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_large))
                    )
                    Text(
                        text = stringResource(R.string.contact_phone),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.letter_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_large))
                    )
                    Text(
                        text = stringResource(R.string.contact_email),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.title_articles),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                /**
                 *
                 *    To do LazyColumn of news
                 *
                 * */
            }
        }
    }
}

@Preview
@Composable
fun MainMenuPreview() {
    StoveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen()
        }
    }
}
