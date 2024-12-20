package com.bajetin.app.features.main.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bajetin.app.data.entity.TransactionCategoryEntity

@Composable
fun CategoryChips(
    categories: List<TransactionCategoryEntity>,
    categorySelected: TransactionCategoryEntity?,
    onClickCategory: (TransactionCategoryEntity) -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),

    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        LazyRow(state = lazyListState, contentPadding = PaddingValues(start = 16.dp)) {
            itemsIndexed(items = categories) { index, item ->
                CategoryChip(
                    leadingIcon = item.emoticon,
                    label = item.label,
                    isSelected = categorySelected == item,
                    onClick = {
                        onClickCategory(item)
                    },
                    modifier = Modifier.height(ButtonDefaults.MinHeight)
                )
                Spacer(
                    modifier = Modifier.width(
                        if (index == categories.size - 1) 16.dp else 4.dp
                    )
                )
            }
        }

        Box(
            modifier = Modifier.width(24.dp).height(ButtonDefaults.MinHeight)
                .align(Alignment.CenterEnd)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.0f),
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                            MaterialTheme.colorScheme.surface.copy(alpha = 1f),
                        )
                    ),
                )
        )
    }
}

@Composable
fun CategoryChip(
    leadingIcon: String?,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        isSelected,
        leadingIcon = { leadingIcon?.let { Text(it) } },
        label = { Text(label) },
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke((0.5).dp, MaterialTheme.colorScheme.surfaceContainerHigh),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = Color.White,
        ),
        modifier = modifier,
    )
}
