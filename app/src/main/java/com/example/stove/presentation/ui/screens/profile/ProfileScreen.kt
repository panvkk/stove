package com.example.stove.presentation.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stove.R
import com.example.stove.presentation.navigation.NavigationDestination
import com.example.stove.presentation.ui.component.IconWithBackground
import com.example.stove.presentation.ui.component.ProfilePlaceholder
import com.example.stove.presentation.ui.component.shimmer
import com.example.stove.presentation.ui.theme.StoveTheme
import com.example.stove.presentation.viewmodel.ProfileUiState
import com.example.stove.presentation.viewmodel.ProfileViewModel
import com.example.stove.presentation.viewmodel.UserUiState

object ProfileDestination : NavigationDestination {
    override val route: String = "Profile"
    override val titleRes: Int = R.string.title_profile
}

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onClickFavourites: () -> Unit
) {
    val profileUiState by viewModel.profileUiState.collectAsState()

    Column(
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large),
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = dimensionResource(R.dimen.padding_large),
                    bottom = dimensionResource(R.dimen.padding_large)
                )
        ) {
            Surface(
                shape = RoundedCornerShape(dimensionResource(R.dimen.fully_rounded_avatar)),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large))
            ) {
                Image(
                    painter = painterResource(R.drawable.empty_avatar),
                    contentDescription = stringResource(R.string.avatar_description),
                    modifier = Modifier.size(dimensionResource(R.dimen.avatar_size))
                )
            }
            val currentUiState = profileUiState
            if(currentUiState is ProfileUiState.User) {
                when(currentUiState.state) {
                    is UserUiState.Loading -> {
                        ProfilePlaceholder(modifier = Modifier.shimmer(8.dp))
                    }
                    is UserUiState.Failure -> {
                        Text(
                            text = "Ошибка: ${currentUiState.state.errorMessage}",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    is UserUiState.Success -> {
                        Text(
                            text = currentUiState.state.fullName ,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = currentUiState.state.phoneNumber,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_large))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.orders_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_large))
                    )
                    Text(
                        text = stringResource(R.string.menu_orders),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.arrow_forward_icon),
                    contentDescription = null
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_large))
                    .clickable{ onClickFavourites() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.favourites_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_large))
                    )
                    Text(
                        text = stringResource(R.string.menu_favorites),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.arrow_forward_icon),
                    contentDescription = null
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.logout() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.logout_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_large))
                    )
                    Text(
                        text = stringResource(R.string.menu_logout),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.arrow_forward_icon),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    StoveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
//            ProfileScreen()
        }
    }
}