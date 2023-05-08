package com.goskate.goskate.ui.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.goskate.goskate.ui.theme.Shapes

@Composable
fun ButtonWithCornerShape(
    modifier: Modifier,
    text: String,
) {
    Button(
        onClick = {},
        modifier = modifier,
        shape = Shapes.medium,
    ) {
        Text(text = text)
    }
}
