package com.goskate.goskate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goskate.goskate.ui.theme.BlueDark10
import com.goskate.goskate.ui.theme.Shapes
import com.goskate.goskate.ui.theme.black

@Composable
fun Banner(image: ImageVector?, description: String) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .background(BlueDark10, Shapes.medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (image != null) {
            Image(
                imageVector = image,
                contentDescription = "",
                modifier = Modifier
                    .padding(8.dp),
            )
        }
        Text(
            text = description,
            color = black,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .wrapContentWidth()
                .padding(8.dp),
        )
    }
}
