package com.bajetin.app.features.main.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bajetin.composeapp.generated.resources.Res
import bajetin.composeapp.generated.resources.ic_arrow_down
import bajetin.composeapp.generated.resources.ic_calendar_date
import bajetin.composeapp.generated.resources.ic_pen_square
import com.bajetin.app.core.utils.Constants
import com.bajetin.app.core.utils.containsOperators
import com.bajetin.app.core.utils.formatToCurrency
import com.bajetin.app.core.utils.toDisplayDate
import com.bajetin.app.features.main.presentation.component.CategoryChips
import com.bajetin.app.features.main.presentation.component.NumpadRow
import com.bajetin.app.ui.component.ButtonIcon
import org.jetbrains.compose.resources.painterResource

@Composable
fun AddTransactionSheet(
    viewModel: AddTransactionViewModel,
    modifier: Modifier = Modifier,
    onEventLaunch: (uiEvent: AddTransactionUiEvent) -> Unit,
) {
    val addTransactionUiState = viewModel.addTransactionUiState.collectAsStateWithLifecycle().value
    val categoryUiState = viewModel.categoryUiState.collectAsStateWithLifecycle().value
    val localKeyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            onEventLaunch(event)
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ButtonIcon(
            onClick = { viewModel.onClickDatePicker() },
            label = addTransactionUiState.dateMillis.toDisplayDate(),
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_calendar_date),
                    "date",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = it
                )
            },
            trailingIcon = { modifier ->
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_down),
                    "date",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = modifier.size(18.dp)
                )
            },
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.align(alignment = Alignment.Start).padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        AmountExpressionColumn(
            addTransactionUiState.expression,
            addTransactionUiState.amount,
            addTransactionUiState.expression.containsOperators()
        )

        Spacer(Modifier.size(12.dp))
        NoteTextField(
            value = addTransactionUiState.notes,
            onValueChange = {
                if (it.length <= Constants.MaxLengthNote) viewModel.addNotes(it)
            },
            modifier = Modifier.fillMaxWidth()
                .wrapContentSize()
        )
        Spacer(Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CategoryChips(
                categories = categoryUiState,
                categorySelected = addTransactionUiState.categorySelected,
                onClickCategory = {
                    viewModel.selectCategory(it)
                    localKeyboard?.hide()
                },
                modifier = Modifier.weight(1F)
            )
            ButtonIcon(
                onClick = {
                },
                label = "More",
                trailingIcon = { modifier ->
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_down),
                        "category",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = modifier.align(Alignment.CenterVertically)
                    )
                },
                buttonColors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
            )
        }
        Spacer(Modifier.size(16.dp))

        NumpadRow(
            onKeyPress = viewModel::onKeyPress,
            onClickDone = viewModel::onClickSave,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun AmountExpressionColumn(
    expression: String,
    amount: String,
    isExpressionContainsOperator: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            if (isExpressionContainsOperator) expression else "",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("Rp.")
            AmountText(amount.formatToCurrency())
        }
    }
}

@Composable
fun AmountText(text: String) {
    var previousText by remember { mutableStateOf(text) }
    val isRemoving = remember(text, previousText) {
        when {
            text.length > previousText.length -> false
            text.length < previousText.length -> true
            else -> false // Default to adding if lengths are equal
        }
    }

    val lastCharOffset = remember { Animatable(0f) }
    LaunchedEffect(text) {
        previousText = text
        val initialOffset = if (isRemoving) -30F else 30F
        lastCharOffset.snapTo(initialOffset) // Start animation at offset
        lastCharOffset.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 100,
                easing = FastOutSlowInEasing
            )
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text.dropLast(1),
            style = MaterialTheme.typography.displayMedium
        )
        AnimatedLastCharacter(text.last(), lastCharOffset.value)
    }
}

@Composable
private fun AnimatedLastCharacter(char: Char, offsetY: Float) {
    Text(
        text = char.toString(),
        modifier = Modifier
            .offset(y = offsetY.dp)
            .graphicsLayer {
                alpha = if (offsetY != 0f) 0.3f else 1f
            },
        style = MaterialTheme.typography.displayMedium
    )
}

@Composable
private fun NoteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.background(
            color = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.medium
        ).border(
            (0.5).dp,
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            shape = MaterialTheme.shapes.medium
        ).padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_pen_square),
            "note",
            modifier = Modifier.padding(start = 12.dp).size(16.dp)
        )
        Box(
            modifier = Modifier,
        ) {
            if (value.isEmpty()) {
                Text(
                    text = "Add notes..",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            )
        }
    }
}
