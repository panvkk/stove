package com.example.stove.presentation.ui.screens.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.stove.R
import com.example.stove.presentation.ui.component.CustomButton
import com.example.stove.presentation.ui.component.InputField
import com.example.stove.presentation.ui.theme.StoveTheme
import com.example.stove.presentation.viewmodel.AuthUiState
import com.example.stove.presentation.viewmodel.AuthViewModel
import com.example.stove.presentation.viewmodel.LoginUiState
import com.example.stove.presentation.viewmodel.AuthNavigatinoEvent

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvent.collect { event ->
            when(event) {
                is AuthNavigatinoEvent.ToRegister ->
                    navController.navigate("auth_graph/register")
                else -> {  }
            }
        }
    }

    val inputInfo by viewModel.inputInfo.collectAsStateWithLifecycle()
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
                    labelId = R.string.hint_email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    value = inputInfo.email,
                    modifier = Modifier.fillMaxWidth().padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
                InputField(
                    labelId = R.string.hint_password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    value = inputInfo.password,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().padding(
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
            Text(
                text = stringResource(R.string.link_forgot_password),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.clickable { TODO() }
            )
            val currentUiState = authUiState
            if(currentUiState is AuthUiState.Login) {
                if(currentUiState.state is LoginUiState.Failure) {
                    Text(
                        text = currentUiState.state.message,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
            Column {
                CustomButton(
                    labelId = R.string.button_login,
                    isActiveButton = true,
                    onClickBehavior = { viewModel.login() },
                    textStyle = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        //.height(dimensionResource(R.dimen.active_button_height))
                        .padding(
                            top = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                )
                CustomButton(
                    labelId = R.string.button_guest,
                    isActiveButton = false,
                    onClickBehavior = { TODO() },
                    textStyle = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth().padding(
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.text_no_account),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_medium)
                )
            )
            CustomButton(
                labelId = R.string.button_register,
                isActiveButton = false,
                textStyle = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_medium)
                ),
                onClickBehavior = { viewModel.toRegistration() }
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    StoveTheme {
        Surface(
//            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
//            LoginScreen()
        }
    }
}