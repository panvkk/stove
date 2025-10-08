package com.example.stove.presentation.ui.component

import android.net.Uri
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.stove.R
import com.example.stove.data.favourite.Favourite

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    labelId: Int,
    isActiveButton: Boolean,
    textStyle: TextStyle,
    onClickBehavior: () -> Unit
) {
    Button(
        onClick = onClickBehavior,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            contentColor = if(isActiveButton) MaterialTheme.colorScheme.background
                    else MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = if(isActiveButton) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Text(
            text = stringResource(labelId),
            style = textStyle
        )
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    labelId: Int,
    onValueChange: (String) -> Unit,
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(stringResource(labelId)) },
        visualTransformation = visualTransformation,
        textStyle = MaterialTheme.typography.labelSmall,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,

            //turning off bottom line
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun IconWithBackground(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.size(dimensionResource(R.dimen.background_icon_size))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size))

            )
        }
    }
}


// New from the server, need API now
@Composable
fun Article(
    modifier: Modifier = Modifier,
    articleTitle: String,
    articleBody: String,
    articleImageUri: Uri
) {
    Row {
        Column{
            Text(
                text = articleTitle,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = articleBody,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

    }
}


@Composable
fun DesignerOptionCard(
    title: String,
    imageUri: Uri,
    isSelected: Boolean,
    onClickBehavior: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))) {
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor =
                    if (isSelected) MaterialTheme.colorScheme.tertiaryContainer
                    else MaterialTheme.colorScheme.background,
            ),
            modifier = modifier
                .clickable { onClickBehavior() }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .padding(
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                ) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DesignerButtonsRow(
    backBehavior: () -> Unit,
    nextBehavior: () -> Unit
) {
    Row(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_large))) {
        CustomButton(
            labelId = R.string.button_back,
            textStyle = MaterialTheme.typography.labelLarge,
            isActiveButton = false,
            onClickBehavior = backBehavior,
            modifier = Modifier
                .padding(end = dimensionResource(R.dimen.padding_small))
                .weight(1f)
        )
        CustomButton(
            labelId = R.string.button_next,
            textStyle = MaterialTheme.typography.labelLarge,
            isActiveButton = true,
            onClickBehavior = nextBehavior,
            modifier = Modifier
                .padding(end = dimensionResource(R.dimen.padding_small))
                .weight(1f)
        )
    }
}

@Composable
fun LargeFavouriteCard(
    favourite: Favourite,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.card_elevation)),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))

        ) {
            Text(
                text = favourite.type,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .padding(end = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun SmallFavouriteCard(
    title: String,
    description: String,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.card_elevation)),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                // Need to add placeholder and error
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun Modifier.shimmer(cornerRadius: Dp = 0.dp) : Modifier {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.3f),
        Color.White.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.3f)
    )
    val transition = rememberInfiniteTransition(label = "Shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = -400f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1600,
                easing = FastOutSlowInEasing
            )
        ),
        label = "Translate"
    )
    return this.drawWithCache {
        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(translateAnim, 0f),
            end = Offset(translateAnim + size.width / 1.5f, size.height)
        )
        val cornerPx = cornerRadius.toPx()
        onDrawWithContent {
            drawRoundRect(
                brush = brush,
                cornerRadius = CornerRadius(cornerPx, cornerPx),
                size = size
            )
        }
    }
}