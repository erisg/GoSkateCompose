package com.goskate.goskate.ui.screen.login

import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.OriginalSize
import com.goskate.goskate.R
import com.goskate.goskate.ui.components.ButtonWithCornerShape
import com.goskate.goskate.ui.components.TextFieldWithIconsComponent
import com.goskate.goskate.ui.viewmodels.AuthViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SignUpScreen(navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val userInformation = viewModel.signUpState.collectAsState()

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    var isError by remember {
        mutableStateOf(false)
    }

    var isLoading by remember(key1 = userInformation.value.isLoading) {
        mutableStateOf(userInformation.value.isLoading)
    }
    val isSuccess by remember(key1 = userInformation.value.data) {
        mutableStateOf(userInformation.value.data)
    }
    val isErrorData by remember(key1 = userInformation.value.messageError) {
        mutableStateOf(userInformation.value.messageError)
    }

    if (isSuccess != null) {
        navController.navigate("map")
    }

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val imageLoader = ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = R.drawable.goskate)
                    .apply(block = fun ImageRequest.Builder.() {
                        size(OriginalSize)
                    }).build(),
                imageLoader = imageLoader,
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(50.dp)),
        )
        Text(
            text = stringResource(id = R.string.welcome_message_sigin),
            modifier = Modifier
                .wrapContentHeight(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        TextFieldWithIconsComponent(
            modifier = Modifier
                .fillMaxWidth(),
            label = stringResource(id = R.string.name),
            placeHolder = stringResource(id = R.string.enter_name),
            imageVector = Icons.Default.Person,
            isPassword = false,
            onValueChange = {
                name.value = it
                isError = false
            },
            isErrorFormat = false,
        )
        TextFieldWithIconsComponent(
            modifier = Modifier
                .fillMaxWidth(),
            label = stringResource(id = R.string.email_address),
            placeHolder = stringResource(id = R.string.enter_email),
            imageVector = Icons.Default.Email,
            isPassword = false,
            onValueChange = {
                email.value = it
                isError = false
            },
            isErrorFormat = false,
        )

        TextFieldWithIconsComponent(
            modifier = Modifier
                .fillMaxWidth(),
            label = stringResource(id = R.string.password),
            placeHolder = stringResource(id = R.string.enter_password),
            imageVector = Icons.Default.Lock,
            isPassword = true,
            onValueChange = {
                password.value = it
                isError = false
            },
            isErrorFormat = false,
        )
        TextFieldWithIconsComponent(
            modifier = Modifier
                .fillMaxWidth(),
            label = stringResource(id = R.string.confirm_password),
            placeHolder = stringResource(id = R.string.password),
            imageVector = Icons.Default.Lock,
            isPassword = true,
            onValueChange = {
                confirmPassword.value = it
                isError = false
            },
            isErrorFormat = false,
        )
        TextFieldWithIconsComponent(
            modifier = Modifier
                .fillMaxWidth(),
            label = stringResource(id = R.string.age),
            placeHolder = stringResource(id = R.string.add_age),
            imageVector = ImageVector.vectorResource(R.drawable.baseline_calendar_today_24),
            isPassword = false,
            onValueChange = {
                age.value = it
                isError = false
            },
            isErrorFormat = false,
        )
        if (isError) {
            Text(
                text = stringResource(id = R.string.error_sign_in),
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        if (isErrorData.isNotEmpty()) {
            isLoading = false
            Text(
                text = isErrorData,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ButtonWithCornerShape(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.sign_in),
            onClick = {
                if (name.value.isNotEmpty() &&
                    email.value.isNotEmpty() &&
                    password.value.isNotEmpty() &&
                    confirmPassword.value.isNotEmpty() &&
                    age.value.isNotEmpty()
                ) {
                    if (password.value == confirmPassword.value) {
                        viewModel.signUp(
                            email = email.value,
                            password = password.value,
                            name = name.value,
                            age = age.value,
                        )
                    } else {
                        isError = true
                    }
                } else {
                    isError = true
                }
            },
            isLoading = isLoading,
        )
        Spacer(
            modifier = Modifier
                .height(20.dp),
        )
    }
}
