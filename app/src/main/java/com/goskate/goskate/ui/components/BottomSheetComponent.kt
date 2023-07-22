package com.goskate.goskate.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goskate.goskate.R
import com.goskate.goskate.ui.theme.BlueDark
import com.goskate.goskate.ui.theme.GoSkateTheme
import com.goskate.goskate.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetComponent(
    onDismiss: () -> Unit,
    onCreateRute: () -> Unit,
    onShareLink: () -> Unit
    ) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = BlueDark,
    ) {
        Text(
            text = "Tercer Milenio",
            color = white,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 16.dp),
        )
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .wrapContentWidth(),
        ) {
            Text(
                text = "Hoy",
                color = white,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(painter = painterResource(id = R.drawable.baseline_skateboarding_24), contentDescription = "skate")
        }
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .wrapContentWidth(),
        ) {
            Banner(
                image = null,
                description = "Como llegar",
                onAction = {
                    onCreateRute.invoke()
                })
            Spacer(modifier = Modifier.width(10.dp))
            Banner(
                image = null,
                description = "Compartir",
                onAction = {
                onShareLink.invoke()
                })
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Direccion",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = white,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Text(
            text = "calle 67 # 55 - 87 sur",
            color = white,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(count = 6) {
                ItemImage(
                    image = R.drawable.skateparkimg,
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun ItemImage(image: Int) {
    Image(
        painter = painterResource(id = image),
        contentDescription = "",
        modifier = Modifier
            .size(300.dp)
            .animateContentSize()
            .clip(RoundedCornerShape(16))
            .fillMaxSize()
            .clickable {
            },
        contentScale = ContentScale.FillWidth,
    )
}

@Preview(showBackground = true)
@Composable
fun BottomSheetPreview() {
    GoSkateTheme {
        BottomSheetComponent({},{},{})
    }
}
