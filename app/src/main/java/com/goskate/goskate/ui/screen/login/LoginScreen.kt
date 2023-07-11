package com.goskate.goskate.ui.screen.login

import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.OriginalSize
import com.goskate.goskate.R
import com.goskate.goskate.ui.components.ButtonWithCornerShape
import com.goskate.goskate.ui.components.TextFieldWithIconsComponent
import com.goskate.goskate.ui.components.isValidEmail
import com.goskate.goskate.ui.theme.GoSkateTheme
import com.goskate.goskate.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val userInformation = viewModel.signInState.collectAsState()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isEmailError = remember { mutableStateOf(false) }
    val isEmptyData = remember { mutableStateOf(false) }

    var isLoading by remember(key1 = userInformation.value.isLoading) {
        mutableStateOf(userInformation.value.isLoading)
    }
    val isSuccess by remember(key1 = userInformation.value.data) {
        mutableStateOf(userInformation.value.data)
    }
    val isError by remember(key1 = userInformation.value.messageError) {
        mutableStateOf(userInformation.value.messageError)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        val context = LocalContext.current

        if (isSuccess != null) {
            navController.navigate("map")
        }

        val imageLoader = ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        Box(
            modifier = Modifier
                .wrapContentSize(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.bc),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.Fit,
            )
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = R.drawable.skate)
                        .apply(block = fun ImageRequest.Builder.() {
                            size(OriginalSize)
                        }).build(),
                    imageLoader = imageLoader,
                ),
                contentDescription = null,
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .align(Alignment.TopCenter),
            )
        }
        Text(
            text = stringResource(id = R.string.welcome_message),
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(
            Modifier
                .height(10.dp),
        )
        TextFieldWithIconsComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            label = stringResource(id = R.string.email_address),
            placeHolder = stringResource(id = R.string.label_email),
            imageVector = Icons.Default.Email,
            isPassword = false,
            onValueChange = {
                email.value = it
                isEmailError.value = false
                isEmptyData.value = false
            },
            isErrorFormat = isEmailError.value,
        )
        if (isEmailError.value) {
            Text(
                text = stringResource(id = R.string.valid_address),
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                fontSize = 12.sp,
            )
        }
        TextFieldWithIconsComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            label = stringResource(id = R.string.password),
            placeHolder = stringResource(id = R.string.enter_password),
            imageVector = Icons.Default.Lock,
            isPassword = true,
            onValueChange = {
                password.value = it
                isEmptyData.value = false
            },
            isErrorFormat = false,
        )
        Spacer(
            Modifier
                .height(16.dp),
        )
        ButtonWithCornerShape(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.login_in),
            onClick = {
                if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                    if (email.value.isValidEmail()) {
                        isEmailError.value = false
                        isEmptyData.value = false
                        viewModel.signIn(email.value, password.value)
                    } else {
                        isEmailError.value = true
                    }
                } else {
                    isEmptyData.value = true
                }
            },
            isLoading = isLoading,
        )
        if (isEmptyData.value) {
            Text(
                text = stringResource(id = R.string.must_enter_email),
                color = Color.Red,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        }
        if (isError.isNotEmpty()) {
            isLoading = false
            Text(
                text = isError,
                color = Color.Red,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        }
        Text(
            text = stringResource(id = R.string.dont_have_account),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            textAlign = TextAlign.Center,
        )
        Spacer(
            Modifier
                .height(8.dp),
        )
        Text(
            text = stringResource(id = R.string.sign_up),
            modifier = Modifier
                .padding(start = 5.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    navController.navigate("sigIn")
                },
            fontWeight = FontWeight.Bold,
        )
        Spacer(
            Modifier
                .height(16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    GoSkateTheme {
        LoginScreen(navController)
    }
}
