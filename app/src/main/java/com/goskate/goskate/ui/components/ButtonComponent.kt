package com.goskate.goskate.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.goskate.goskate.ui.theme.Purple40
import com.goskate.goskate.ui.theme.Shapes
import com.goskate.goskate.ui.theme.black
import com.goskate.goskate.ui.theme.white

@Composable
fun ButtonWithCornerShape(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean,
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = white,
            contentColor = black,
        ),
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = Purple40),
        shape = Shapes.medium,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.Black,
                modifier = Modifier.size(20.dp),
            )
        } else {
            Text(text = text)
        }
    }
}

@Composable
fun ButtonWithCornerIcon(
    modifier: Modifier,
    text: String,
) {
    Button(
        onClick = {},
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = Purple40),
        shape = Shapes.medium,
    ) {
        Text(text = text)
    }
}
