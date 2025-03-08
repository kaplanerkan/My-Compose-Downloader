package com.mycomposedownloader.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mycomposedownloader.ui.ui.theme.MyComposeDownloaderTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

data class ContentCard(
    val title: String,
    val description: String,
    val imageUrl: String,
    val actionUrl: String
)



class MyCardExample : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyComposeDownloaderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding) // Scaffold'un padding'ini uygula
                    ) {
                        FilledCardExample()
                        ElevatedCardExample()
                    }
                }
            }
        }
    }
}




@Composable
fun FilledCardExample() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier.padding(16.dp)
            .size(width = 240.dp, height = 100.dp),

    ) {
        Text(
            text = "Filled",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilledCardExamplePreview() {
    MyComposeDownloaderTheme {
        FilledCardExample()
    }
}


@Composable
fun ElevatedCardExample() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.padding(16.dp)
            .size(width = 240.dp, height = 100.dp)
    ) {
        Text(
            text = "Elevated",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ElevatedCardExamplePreview() {
    MyComposeDownloaderTheme {
        ElevatedCardExample()
    }
}




@Composable
fun OutlinedCardExample() {
    OutlinedCard(
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.padding(16.dp)
            .size(width = 240.dp, height = 100.dp)
    ) {
        Text(
            text = "Outlined",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedCardExamplePreview() {
    MyComposeDownloaderTheme {
        OutlinedCardExample()
    }
}
















@Composable
fun ContentCardView(card: ContentCard) {

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(card.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = card.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = card.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){
                Button(
                    shape = MaterialTheme.shapes.extraSmall,
                    elevation = ButtonDefaults.buttonElevation(5.dp),
                    onClick = { /* TODO: Handle click action */ }) {
                    Text(text = "Learn More")
                }
                Button(
                    shape = MaterialTheme.shapes.extraSmall,
                    elevation = ButtonDefaults.buttonElevation(5.dp),
                    onClick = { /* TODO: Handle click action */ }) {
                    Text(text = "Learn More")
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposeDownloaderTheme {
        val contentCard = ContentCard(
            title = "Kotlin Öğrenmeye Başlayın",
            description = "Kotlin, JVM, Android, tarayıcı ve yerel platformlar için modern bir programlama dilidir.",
            imageUrl = "https://www.tierenzyklopaedie.de/wp-content/uploads/2023/12/patagonischer-pinguin.jpg",
            actionUrl = "https://kotlinlang.org"
        )
        ContentCardView(contentCard)
    }
}