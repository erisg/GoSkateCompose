package com.goskate.goskate.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import com.goskate.goskate.ui.theme.Shapes

@Composable
fun TextFieldWithIcons(
    modifier: Modifier,
    label: String,
    placeHolder: String,
    imageVector: ImageVector
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    return OutlinedTextField(
        value = text,
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = imageVector.toString()) },
        onValueChange = {
            text = it
        },
        modifier = modifier,
        label = { Text(text = label) },
        placeholder = { Text(text = placeHolder) },
        shape = Shapes.medium
    )
}