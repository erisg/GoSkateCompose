package com.goskate.goskate.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.goskate.goskate.R
import com.goskate.goskate.ui.theme.GoSkateTheme
import com.goskate.goskate.ui.theme.Pink80
import com.goskate.goskate.ui.theme.Shapes
import com.goskate.goskate.ui.theme.black
import com.goskate.goskate.ui.theme.gray
import com.goskate.goskate.ui.theme.white
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerComponent(modifier: Modifier) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    var preSelectedItem by remember(key1 = "Gender") {
        mutableStateOf("Gender")
    }
    var isCardClicked by remember {
        mutableStateOf(false)
    }
    var isSelectItem by remember {
        mutableStateOf(false)
    }
    val borderColor = if (isCardClicked) black else gray
    var flag by remember { mutableStateOf(true) }
    val resourceId =
        remember(flag) { if (flag) R.drawable.ic_action_expanded else R.drawable.ic_action_expanded_less }
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    ConstraintLayout(modifier = modifier) {
        val (textField, divider, dialog) = createRefs()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(textField) {},
        ) {
            Card(
                border = BorderStroke(0.5.dp, color = borderColor),
                onClick = {
                    scope.launch {
                        if (flag) {
                            rotation.animateTo(
                                targetValue = 180f,
                                animationSpec = tween(150, easing = LinearEasing),
                            )
                        } else {
                            rotation.animateTo(
                                targetValue = -180f,
                                animationSpec = tween(150, easing = LinearEasing),
                            )
                        }
                        flag = flag.not()
                        rotation.snapTo(0f)
                    }
                    isCardClicked = isCardClicked.not()
                    showDialog = showDialog.not()
                },
                colors = CardDefaults.cardColors(containerColor = white),
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .height(54.dp)
                                .padding(
                                    start = 16.dp,
                                    end = 30.dp,
                                ),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = preSelectedItem,
                                modifier = Modifier.padding(top = 2.dp),
                                fontSize = 16.sp,
                                color = black,
                            )
                        }
                        Image(
                            painterResource(id = resourceId),
                            contentDescription = "action",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .rotate(rotation.value),
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .constrainAs(divider) {
                    top.linkTo(textField.bottom)
                    start.linkTo(textField.start)
                    end.linkTo(textField.end)
                },
        )
        if (showDialog) {
            LanguageDialog(
                modifier = Modifier.constrainAs(dialog) {
                    top.linkTo(divider.bottom)
                    start.linkTo(divider.start)
                    end.linkTo(divider.end)
                },
                preSelectedItem = preSelectedItem,
                selectedItem = {
                    isSelectItem = true
                    preSelectedItem = it
                    isCardClicked = false
                    showDialog = false
                },
            )
        }
    }
}

@Composable
fun LanguageDialog(
    modifier: Modifier = Modifier,
    preSelectedItem: String,
    selectedItem: (String) -> Unit,
) {
    val (selected, setSelected) = remember {
        mutableStateOf("")
    }
    if (selected.isNotEmpty()) {
        selectedItem.invoke(selected)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        border = BorderStroke(1.dp, black),
        shape = Shapes.medium.copy(
            CornerSize(12.dp),
        ),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = white),
    ) {
        LanguageItems(selected = selected, preSelectedItem, onClickItem = setSelected)
    }
}

@Composable
fun LanguageItems(
    selected: String,
    preSelectedItem: String,
    onClickItem: (selected: String) -> Unit,
) {
    val rememberScroll = rememberLazyListState()
    val languages = stringArrayResource(id = R.array.genders)
    LazyColumn(
        modifier = Modifier
            .padding(start = 16.dp),
        state = rememberScroll,
    ) {
        items(languages) { text ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(end = 20.dp)
                    .clickableNoRipple {
                        onClickItem(text)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    color = black,
                    modifier = Modifier.clickableNoRipple {
                        onClickItem(text)
                    },
                )
                if (preSelectedItem.isNotEmpty()) {
                    Row(
                        modifier = Modifier.size(24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    },
                                ) {
                                    onClickItem(text)
                                }
                                .border(
                                    border = if (text == preSelectedItem) {
                                        BorderStroke(width = 5.dp, color = Pink80)
                                    } else {
                                        BorderStroke(width = 0.5.dp, color = black)
                                    },
                                    shape = Shapes.medium,
                                ),
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.size(24.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clickableNoRipple {
                                    onClickItem(text)
                                }
                                .border(
                                    border = if (text == selected) {
                                        BorderStroke(width = 5.dp, color = Pink80)
                                    } else {
                                        BorderStroke(width = 0.5.dp, color = black)
                                    },
                                    shape = Shapes.medium,
                                ),
                        )
                    }
                }
            }
            Divider(color = black)
        }
    }
}

fun Modifier.clickableNoRipple(onClickItem: () -> Unit) = composed(
    factory = {
        Modifier.clickable(
            indication = null,
            interactionSource = remember {
                MutableInteractionSource()
            },
        ) {
            onClickItem()
        }
    },
)

@Preview(showBackground = true)
@Composable
fun SpinnerComponentPreview() {
    GoSkateTheme {
        SpinnerComponent(Modifier.fillMaxWidth())
    }
}
