package com.dicoding.animelist

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.animelist.ui.theme.AnimeListTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeListTheme {
                Profile()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(modifier: Modifier = Modifier) {

    val name: String by remember { mutableStateOf("") }
    val context = LocalContext.current
    val activity = context.findActivity()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "My Profile")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { activity?.finish() },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    model = "https://media.licdn.com/dms/image/D5603AQEI8vgEWN61Ow/profile-displayphoto-shrink_800_800/0/1692859490906?e=1704326400&v=beta&t=UjepsQLXteLFnPW9P3rB8b2_-qruaxg_a12gEip_GLU",
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .border(
                            width = 10.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .size(200.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Fitri Sagita",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 31.sp,
                )
                Text(
                    text = "Fitristarius@gmail.com",
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    AnimeListTheme {
        Profile()
    }
}