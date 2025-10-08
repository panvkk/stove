package com.example.stove.presentation.ui.screens.designer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stove.R
import com.example.stove.presentation.ui.component.CustomButton
import com.example.stove.presentation.ui.component.DesignerButtonsRow
import com.example.stove.presentation.ui.component.InputField
import com.example.stove.presentation.viewmodel.DesignerViewModel

@Composable
fun DesignerNameScreen(
    viewModel: DesignerViewModel,
    nextBehavior: () -> Unit,
    backBehavior: () -> Unit
) {
    val draft by viewModel.draft.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large)
        )
    ) {
        InputField(
            labelId = R.string.hint_draft_name,
            onValueChange = { viewModel.updateDraftName(it) },
            value = draft.draftName,
            modifier = Modifier
                .padding(vertical = dimensionResource(R.dimen.padding_medium))
                .fillMaxWidth()
        )
        DesignerButtonsRow(
            backBehavior = { backBehavior() },
            nextBehavior = { nextBehavior() }
        )
    }
}