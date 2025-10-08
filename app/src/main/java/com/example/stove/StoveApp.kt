package com.example.stove

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stove.presentation.navigation.StoveNavGraph


@Composable
fun StoveApp(navController: NavHostController = rememberNavController()) {
    StoveNavGraph(navController = navController)
}

enum class StoveMenus(val number: Int) {
    HOME(1),
    DESIGNER(2),
    PROFILE(3)
}

private data class MainScreen(
    val type: StoveMenus,
    val passiveIconId: Int,
    val activeIconId: Int,
    val labelId: Int,
    val navigate: () -> Unit
)

@Composable
fun StoveBottomAppBar(
    navigateHome: () -> Unit,
    navigateDesigner: () -> Unit,
    navigateProfile: () -> Unit,
    isSelected: StoveMenus,
    modifier: Modifier = Modifier
) {
    val navigationIconColors = NavigationBarItemColors(
        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
        unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
        unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        disabledIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
        disabledTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        selectedIndicatorColor = MaterialTheme.colorScheme.background
    )

    NavigationBar(
        modifier = modifier.navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        val screens = listOf(
            MainScreen(
                type = StoveMenus.HOME,
                passiveIconId = R.drawable.home_passive,
                activeIconId = R.drawable.home_active,
                labelId = R.string.nav_home,
                navigate = navigateHome
            ),
            MainScreen(
                type = StoveMenus.DESIGNER,
                passiveIconId = R.drawable.designer_passive,
                activeIconId = R.drawable.designer_active,
                labelId = R.string.nav_constructor,
                navigate = navigateDesigner
            ),
            MainScreen(
                type = StoveMenus.PROFILE,
                passiveIconId = R.drawable.profile_passive,
                activeIconId = R.drawable.profile_active,
                labelId = R.string.nav_profile,
                navigate = navigateProfile
            )
        )
        screens.forEach { screen ->
            val selected = screen.type == isSelected

            val scale by animateFloatAsState(
                targetValue = if(selected) 0.9f else 1.0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = if(selected)
                            painterResource(screen.activeIconId)
                        else
                            painterResource(screen.passiveIconId),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.labelId),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                selected = selected,
                onClick = screen.navigate,
                colors = navigationIconColors,
                modifier = Modifier.scale(scale)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoveTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {  }
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_backward_icon),
                        contentDescription = stringResource(R.string.arrow_backward_description),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier.statusBarsPadding()
    )
}