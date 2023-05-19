package com.goskate.goskate.ui.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.goskate.goskate.R
import com.goskate.goskate.ui.components.TextField
import com.goskate.goskate.ui.components.TextFieldWithIcons

@Composable
fun SigInScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.padding(vertical = 34.dp),
        )
        TextFieldWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            label = "Name",
            placeHolder = "Enter your name",
            imageVector = Icons.Default.Person,
        )
        TextFieldWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            label = "Email address",
            placeHolder = "Enter your e-mail",
            imageVector = Icons.Default.Email,
        )

        TextFieldWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            label = "Password",
            placeHolder = "Enter your password",
            imageVector = Icons.Default.Lock,
        )
        TextFieldWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            label = "Confirm Password",
            placeHolder = "Confirm password",
            imageVector = Icons.Default.Lock,
        )
    }
}
