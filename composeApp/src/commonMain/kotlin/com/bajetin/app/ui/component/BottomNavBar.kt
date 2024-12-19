package com.bajetin.app.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bajetin.app.navigation.BottomNavItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    bottomNavItems: List<BottomNavItem>,
    onItemSelected: (BottomNavItem) -> Unit,
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        bottomNavItems.iterator().forEach { item ->
            val isSelected = item.route == currentRoute

            val isAddItem = item == BottomNavItem.Add

            NavigationBarItem(
                icon = {
                    item.icon?.let {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },

                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = MaterialTheme.colorScheme.outline,
                    indicatorColor = if (isAddItem) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    },
                    selectedIconColor = if (isAddItem) {
                        MaterialTheme.colorScheme.onSecondary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                ),
                selected = if (isAddItem) true else isSelected,
                onClick = { onItemSelected(item) }
            )
        }
    }
}
