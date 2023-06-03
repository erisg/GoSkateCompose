package com.goskate.goskate.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.goskate.goskate.ui.theme.Shapes
import com.goskate.goskate.ui.theme.black
import com.goskate.goskate.ui.theme.gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithIconsComponent(
    modifier: Modifier,
    label: String,
    placeHolder: String,
    imageVector: ImageVector,
    isPassword: Boolean
) {
    var text by rememberSaveable { mutableStateOf("") }
    return OutlinedTextField(
        value = text,
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = imageVector.toString()) },
        onValueChange = {
            text = it
        },
        modifier = modifier,
        label = { Text(text = label) },
        placeholder = { Text(text = placeHolder) },
        shape = Shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = black,
            unfocusedBorderColor = gray,
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(
    modifier: Modifier,
    label: String,
    placeHolder: String,
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    return OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
        },
        modifier = modifier,
        label = { Text(text = label) },
        placeholder = { Text(text = placeHolder) },
        shape = Shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = black,
            unfocusedBorderColor = gray,
        ),
    )
}

@Composable
fun QuantityMenuSpinner(
    availableQuantities: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
) {
}
