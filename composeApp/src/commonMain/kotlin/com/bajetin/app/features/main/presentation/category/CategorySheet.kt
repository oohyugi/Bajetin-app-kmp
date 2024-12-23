package com.bajetin.app.features.main.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import bajetin.composeapp.generated.resources.Res
import bajetin.composeapp.generated.resources.add_category_act
import bajetin.composeapp.generated.resources.add_category_title
import com.bajetin.app.data.entity.TransactionCategoryEntity
import org.jetbrains.compose.resources.stringResource

@Composable
fun CategorySheet(
    categories: List<TransactionCategoryEntity>,
    selectedCategory: TransactionCategoryEntity?,
    onSelectedCategory: (TransactionCategoryEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 124.dp)
        ) {
            items(count = categories.size) { index ->
                val category = categories[index]
                CategoryCard(
                    selectedCategory,
                    category,
                    onSelectedCategory,
                )
            }
        }

        Footer(Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun Footer(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(16.dp)
    ) {
        Text(
            stringResource(Res.string.add_category_title),
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.size(16.dp))
        Button(onClick = {}, shape = MaterialTheme.shapes.large) {
            Text(stringResource(Res.string.add_category_act))
        }
    }
}

@Composable
private fun CategoryCard(
    selectedCategory: TransactionCategoryEntity?,
    category: TransactionCategoryEntity,
    onSelectedCategory: (TransactionCategoryEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (selectedCategory == category) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            }
        ),
        onClick = { onSelectedCategory(category) },
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(12.dp)
        ) {
            Text(category.emoticon ?: "\uD83D\uDE00")
            Spacer(Modifier.size(8.dp))
            Text(
                category.label,
                style = MaterialTheme.typography.bodySmall,
                color = if (selectedCategory == category) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
