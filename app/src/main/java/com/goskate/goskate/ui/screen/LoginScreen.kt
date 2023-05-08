package com.goskate.goskate

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.OriginalSize
import com.goskate.goskate.ui.components.ButtonWithCornerShape
import com.goskate.goskate.ui.components.TextFieldWithIcons
import com.goskate.goskate.ui.theme.GoSkateTheme
import com.goskate.goskate.ui.theme.white

@Composable
fun LoginScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (
            header, divider, welcome, dividerContent, email, password,
            dividerEditText, bottom, signUp, loginGoogle
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
        TextFieldWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .constrainAs(email) {
                    top.linkTo(dividerContent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            label = "Email address",
            placeHolder = "Enter your e-mail",
            imageVector = Icons.Default.Email,
        )

        TextFieldWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .constrainAs(password) {
                    top.linkTo(email.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            label = "Password",
            placeHolder = "Enter your password",
            imageVector = Icons.Default.Lock,
        )
        Spacer(
            Modifier
                .height(16.dp)
                .constrainAs(dividerEditText) {
                    top.linkTo(password.bottom)
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
        )
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .constrainAs(signUp) {
                    top.linkTo(bottom.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        ) {
            Text(text = "Aun no tienes una cuenta?")

            Text(
                text = "Registrarse",
                modifier = Modifier.padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
            )
        }
        ButtonWithCornerShape(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .constrainAs(loginGoogle) {
                    top.linkTo(signUp.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "Log with google",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    GoSkateTheme {
        LoginScreen()
    }
}
