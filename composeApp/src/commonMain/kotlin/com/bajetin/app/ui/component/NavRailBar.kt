package com.bajetin.app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bajetin.composeapp.generated.resources.Res
import bajetin.composeapp.generated.resources.ic_add
import com.bajetin.app.navigation.BottomNavItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun NavRailBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    items: List<BottomNavItem>,
    onItemSelected: (BottomNavItem) -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        items.iterator().forEach { item ->
            val isSelected = item.route == currentRoute
            if (item == BottomNavItem.Add) {
                Icon(
                    painter = painterResource(Res.drawable.ic_add),
                    "add transaction",
                    modifier = Modifier.size(46.dp).clickable {
                        onItemSelected(item)
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
                    onClick = { onItemSelected(item) }
                )
            }
        }
    }
}
