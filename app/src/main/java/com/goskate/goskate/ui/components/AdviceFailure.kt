package com.goskate.goskate.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goskate.goskate.R
import com.goskate.goskate.ui.theme.GoSkateTheme
import com.goskate.goskate.ui.theme.Shapes
import com.goskate.goskate.ui.theme.black

@Composable
fun AdviceFailureComponent(@StringRes title: Int, modifier: Modifier = Modifier) {
    Snackbar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .height(81.dp),
        shape = Shapes.medium,
        containerColor = com.goskate.goskate.ui.theme.error,
        contentColor = com.goskate.goskate.ui.theme.error,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(shape = Shapes.medium, color = Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                imageVector = Icons.Default.Warning,
                contentDescription = "failure",
            )
            Text(
                text = stringResource(id = title),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp),
                fontSize = 14.sp,
                color = black,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview
fun AdviceFailurePreview() {
    GoSkateTheme {
        AdviceFailureComponent(title = R.string.welcome_message, modifier = Modifier.fillMaxWidth())
    }
}
