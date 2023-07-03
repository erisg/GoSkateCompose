package com.goskate.goskate.ui.screen.login

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isEmailError = remember { mutableStateOf(false) }
    val isEmptyData = remember { mutableStateOf(false) }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (
            header,
            divider,
            welcome,
            dividerContent,
            emailText,
            passwordText,
            dividerEditText,
            bottom,
            signUp,
        ) = createRefs()

        val context = LocalContext.current

        val imageLoader = ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        Spacer(
            Modifier
                .height(24.dp)
                .constrainAs(divider) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )
        Text(
            text = stringResource(id = R.string.welcome_message),
            modifier = Modifier
                .constrainAs(welcome) {
                    top.linkTo(divider.bottom)
                    start.linkTo(header.start)
                    end.linkTo(header.end)
                },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Box(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(welcome.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.bc),
                contentDescription = null,
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .align(Alignment.BottomCenter),
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
                    .clip(RoundedCornerShape(50.dp)),
            )
        }
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .constrainAs(dividerContent) {
                    top.linkTo(header.bottom)
                    start.linkTo(header.start)
                    end.linkTo(header.end)
                },
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(emailText) {
                    top.linkTo(dividerContent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        ) {
            TextFieldWithIconsComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                label = "Email address",
                placeHolder = "Enter your e-mail",
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
                    text = "Por favor ingresa un correo valido",
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    fontSize = 12.sp,
                )
            }
        }
        TextFieldWithIconsComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .constrainAs(passwordText) {
                    top.linkTo(emailText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            label = "Password",
            placeHolder = "Enter your password",
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
                .height(16.dp)
                .constrainAs(dividerEditText) {
                    top.linkTo(passwordText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )
        ButtonWithCornerShape(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .constrainAs(bottom) {
                    top.linkTo(dividerEditText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "Log in",
            onClick = {
                if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                    if (email.value.isValidEmail()) {
                        viewModel.signIn(email.value, password.value)
                    } else {
                        isEmailError.value = true
                    }
                } else {
                    isEmptyData.value = true
                }
            },
        )
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(horizontal = 24.dp)
                .constrainAs(signUp) {
                    top.linkTo(bottom.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        ) {
            if (isEmptyData.value) {
                Text(
                    text = "Debes ingresar tu email y contraseña",
                    color = Color.Red,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    fontSize = 12.sp,
                )
            }
            Text(
                text = "Aun no tienes una cuenta?",
                modifier = Modifier
                    .padding(top = 15.dp),
            )

            Text(
                text = "Registrarse",
                modifier = Modifier
                    .padding(start = 5.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        navController.navigate("sigIn")
                    },
                fontWeight = FontWeight.Bold,
            )
        }
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
