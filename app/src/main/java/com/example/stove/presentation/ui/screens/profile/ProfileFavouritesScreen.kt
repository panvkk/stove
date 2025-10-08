package com.example.stove.presentation.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.stove.R
import com.example.stove.data.favourite.Favourite
import com.example.stove.presentation.ui.component.LargeFavouriteCard

@Composable
fun ProfileFavouritesScreen() {
    val favouritesList = listOf<Favourite>()

    Column(
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large)
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                top = dimensionResource(R.dimen.padding_large),
                bottom = dimensionResource(R.dimen.padding_large)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = favouritesList, key = { it.id }) { favourite ->
                LargeFavouriteCard(
                    favourite = favourite,
                    modifier = Modifier.padding(
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}