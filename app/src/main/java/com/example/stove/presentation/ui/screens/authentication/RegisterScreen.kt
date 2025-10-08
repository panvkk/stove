package com.example.stove.presentation.ui.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.stove.R
import com.example.stove.presentation.ui.component.CustomButton
import com.example.stove.presentation.ui.component.InputField
import com.example.stove.presentation.viewmodel.AuthUiState
import com.example.stove.presentation.viewmodel.AuthViewModel
import com.example.stove.presentation.viewmodel.AuthNavigatinoEvent
import com.example.stove.presentation.viewmodel.RegisterUiState

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is AuthNavigatinoEvent.ToLogin ->
                    navController.navigate("auth_graph/login")
                else -> { }
            }
        }
    }

    val inputInfo by viewModel.inputInfo.collectAsState()
    val authUiState by viewModel.authUiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(R.dimen.padding_large),
                end = dimensionResource(R.dimen.padding_large)
            )
    ) {
        Column {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                InputField(
                    labelId = R.string.hint_full_name,
                    onValueChange = { viewModel.onFullNameChange(it) },
                    value = inputInfo.fullName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                )

                InputField(
                    labelId = R.string.hint_phone_number,
                    onValueChange = { viewModel.onPhoneNumberChange(it) },
                    value = inputInfo.phoneNumber,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                )

                InputField(
                    labelId = R.string.hint_email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    value = inputInfo.email,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                )

                InputField(
                    labelId = R.string.hint_password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    value = inputInfo.password,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                )
            }
            when(val currentUiState = authUiState) {
                is AuthUiState.Register -> {
                    if(currentUiState.state is RegisterUiState.Failure) {
                        Text(
                            text = "Error: " + currentUiState.state.message,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
                else -> {    }
            }
            CustomButton(
                labelId = R.string.button_register,
                isActiveButton = true,
                onClickBehavior = { viewModel.register() },
                textStyle = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
            )
        }

        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.text_has_account),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_medium)
                )
            )
            CustomButton(
                labelId = R.string.button_login,
                isActiveButton = false,
                textStyle = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_medium)
                ),
                onClickBehavior = { viewModel.toLogin() }
            )
        }
    }
}