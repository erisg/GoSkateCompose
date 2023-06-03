package com.goskate.goskate.ui.screen.login

import android.os.Build
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.OriginalSize
import com.goskate.goskate.R
import com.goskate.goskate.ui.components.ButtonWithCornerShape
import com.goskate.goskate.ui.components.SpinnerComponent
import com.goskate.goskate.ui.components.TextFieldWithIconsComponent

@Composable
fun SigInScreen(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (
            image,
            title,
            content,
        ) = createRefs()
        val context = LocalContext.current

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
                .wrapContentWidth()
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(50.dp))
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )
        Text(
            text = stringResource(id = R.string.welcome_message_sigin),
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.wrapContentHeight(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(horizontal = 24.dp)
                .constrainAs(content) {
                    top.linkTo(title.bottom)
                    bottom.linkTo(parent.bottom)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextFieldWithIconsComponent(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Name",
                placeHolder = "Enter your name",
                imageVector = Icons.Default.Person,
                isPassword = false,
            )
            TextFieldWithIconsComponent(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Email address",
                placeHolder = "Enter your e-mail",
                imageVector = Icons.Default.Email,
                isPassword = false,
            )

            TextFieldWithIconsComponent(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Password",
                placeHolder = "Enter your password",
                imageVector = Icons.Default.Lock,
                isPassword = true,
            )
            TextFieldWithIconsComponent(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Confirm Password",
                placeHolder = "Confirm password",
                imageVector = Icons.Default.Lock,
                isPassword = true,
            )
            TextFieldWithIconsComponent(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Age",
                placeHolder = "Add your age",
                imageVector = ImageVector.vectorResource(R.drawable.baseline_calendar_today_24),
                isPassword = false,
            )
            SpinnerComponent(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            ButtonWithCornerShape(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(),
                text = "Sign In",
                onClick = {
                    navController.navigate("map")
                },
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp),
            )
        }
    }
}
