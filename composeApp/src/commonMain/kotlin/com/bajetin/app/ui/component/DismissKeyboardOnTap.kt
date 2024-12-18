package com.bajetin.app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

fun Modifier.dismissKeyboardOnTap(): Modifier = composed {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        keyboardController?.hide()
        focusManager.clearFocus()
    }
}
