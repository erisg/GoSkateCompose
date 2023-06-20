package com.goskate.goskate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goskate.goskate.ui.theme.BlueDark10
import com.goskate.goskate.ui.theme.GoSkateTheme

@Composable
fun ChipFilterComponent(modifier: Modifier) {
    @Composable
    fun TextChipWithIconVisibility(
        iconId: ImageVector,
        isSelected: Boolean,
        text: String,
        onChecked: (Boolean) -> Unit,
    ) {
        val shape = RoundedCornerShape(8.dp)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    vertical = 2.dp,
                    horizontal = 4.dp,
                )
                .background(
                    color = BlueDark10,
                    shape = shape,
                )
                .clip(shape = shape)
                .clickable {
                    onChecked(!isSelected)
                }
                .padding(4.dp),
        ) {
            if (isSelected) {
                Icon(
                    imageVector = iconId,
                    tint = DarkGray,
                    contentDescription = null,
                )
            }
            Text(
                text = text,
                color = Unspecified,
            )
        }
    }
// View Chip
    val eventsRememberOneState = remember {
        mutableStateOf(false)
    }
    val skateParkRememberOneState = remember {
        mutableStateOf(false)
    }
    val streetRememberOneState = remember {
        mutableStateOf(false)
    }
    LazyRow(modifier = modifier) {
        item {
            TextChipWithIconVisibility(
                iconId = Icons.Default.Check,
                isSelected = eventsRememberOneState.value,
                text = "Eventos",
            ) {
                eventsRememberOneState.value = it
            }
        }
        item {
            TextChipWithIconVisibility(
                iconId = Icons.Default.Check,
                isSelected = skateParkRememberOneState.value,
                text = "SkateParks",
            ) {
                skateParkRememberOneState.value = it
            }
        }
        item {
            TextChipWithIconVisibility(
                iconId = Icons.Default.Check,
                isSelected = streetRememberOneState.value,
                text = "Street",
            ) {
                streetRememberOneState.value = it
            }
        }
    }
}

@Preview
@Composable
fun ChipFilterComponentPreview() {
    GoSkateTheme() {
        ChipFilterComponent(modifier = Modifier.fillMaxWidth())
    }
}
