package com.example.canvasssssss

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.canvasssssss.ui.theme.CanvasssssssTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasssssssTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CanvasScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CanvasScreen(modifier: Modifier = Modifier) {
    var canvasWidth by remember { mutableStateOf(0f) }
    var canvasHeight by remember { mutableStateOf(0f) }

    var imageX by remember { mutableStateOf(0f) }
    var imageY by remember { mutableStateOf(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    LaunchedEffect(isAnimating) {
        if (isAnimating) {
            var lastTime = 0L
            while (isAnimating) {
                withFrameMillis { frameTime ->
                    if (lastTime != 0L) {
                        val dt = (frameTime - lastTime) / 1000f

                        imageX += 150f * dt
                        imageY += 150f * dt

                        if (canvasWidth > 0f && canvasHeight > 0f) {
                            if (imageX > canvasWidth || imageY > canvasHeight) {
                                imageX = 0f
                                imageY = 0f
                            }
                        }
                    }
                    lastTime = frameTime
                }
            }
        }
    }

    val minionsImage: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.minions)

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCircle(
                Color.Magenta,
                100f,
                Offset(100f, 100f)
            )
            drawRect(
                Color.Blue,
                Offset(300f, 200f),
                Size(200f, 200f),
                style = Stroke(width = 5f)
            )

            canvasWidth = size.width
            canvasHeight = size.height

            drawLine(
                Color.Red,
                Offset(200f, 800f),
                Offset(size.width, size.height),
                strokeWidth = 10f
            )

            translate(imageX, imageY) {
                drawImage(minionsImage)
            }
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("畫布寬度 $canvasWidth, 長度 $canvasHeight")

            Button(onClick = { isAnimating = !isAnimating }) {
                Text(if (isAnimating) "停止移動" else "開始移動")
            }
        }
    }
}