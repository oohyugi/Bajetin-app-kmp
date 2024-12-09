package com.bajetin.app.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import bajetin.composeapp.generated.resources.Res
import bajetin.composeapp.generated.resources.ic_add
import com.bajetin.app.navigation.NavigationItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun NavRailBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    items: List<NavigationItem>,
    onNavBarClick: (NavigationItem) -> Unit,
) {
    NavigationRail(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        items.iterator().forEach { item ->
            val isSelected = item.route == currentRoute
            if (item == NavigationItem.Add) {
                Icon(
                    painter = painterResource(Res.drawable.ic_add),
                    "add transaction",
                    modifier = Modifier.size(46.dp).clickable {
                        onNavBarClick(item)
                    }
                )

            } else {
                NavigationRailItem(

                    icon = {
                        item.icon?.let {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    alwaysShowLabel = true,
                    selected = isSelected,
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Gray,
                        indicatorColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = { onNavBarClick(item) }
                )
            }
        }
    }
}
