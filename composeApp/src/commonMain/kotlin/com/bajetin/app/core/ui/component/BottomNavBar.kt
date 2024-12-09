package com.bajetin.app.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import bajetin.composeapp.generated.resources.Res
import bajetin.composeapp.generated.resources.ic_add
import com.bajetin.app.core.ui.theme.DarkPrimaryColor
import com.bajetin.app.navigation.NavigationItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    bottomNavItems: List<NavigationItem>,
    onNavBarClick: (NavigationItem) -> Unit,
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        bottomNavItems.iterator().forEach { item ->
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
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Gray,
                        indicatorColor = DarkPrimaryColor
                    ),
                    selected = isSelected,
                    onClick = { onNavBarClick(item) }
                )
            }
        }
    }
}