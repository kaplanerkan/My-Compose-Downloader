package com.mycomposedownloader.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay


@Composable
fun MyCustomButtons(
    modifier: Modifier = Modifier,
    text: String,
    shape: Shape,
    onClick: () -> Unit,

    ) {

    var isClicked by remember { mutableStateOf(false) }

    val buttonColor by animateColorAsState(
        targetValue = if (isClicked) Color.Green else MaterialTheme.colorScheme.primary,
        animationSpec = tween(durationMillis = 100)
    )

    Button(
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        onClick = {
            isClicked = true
            onClick() // Tıklama olayını dışarı aktar
        }
    ) {
        Text(text)
    }

    LaunchedEffect(isClicked) {
        if (isClicked) {
            delay(300)
            isClicked = false
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMyCustomButtons() {
    MyCustomButtons(
        text = "Button",
        shape = MaterialTheme.shapes.extraSmall,
        onClick = { /* Tıklama olayını burada işleyin */ }
    )
}