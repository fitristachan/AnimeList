package com.dicoding.animelist

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.animelist.ui.theme.AnimeListTheme
import com.dicoding.animelist.viewmodel.AnimeListViewModel
import com.dicoding.animelist.viewmodel.AnimeViewModelFactory

class DetailAnimeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeListTheme {
                DetailAnime()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAnime(
    modifier: Modifier = Modifier,
    animeListViewModel: AnimeListViewModel = viewModel(
        factory = AnimeViewModelFactory.getInstance(LocalContext.current)
    )
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val intent = activity?.intent

    var showDialogDelete by remember { mutableStateOf(false) }

    val title = intent?.getStringExtra("title").toString()

    val animeListByTitle by animeListViewModel.getAnimeByTitle.collectAsState()
    LaunchedEffect(animeListViewModel) {
        animeListViewModel.loadAnimeByTitle(title)
    }

    val state = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialogDelete = true
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Anima",
                    tint = Color.White
                )
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            for (anime in animeListByTitle) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        AsyncImage(
                            model = anime.photo,
                            contentDescription = title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(300.dp)
                                .weight(2f)
                                .clip(RoundedCornerShape(15))
                                .border(
                                    width = 10.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(15)
                                )
                        )
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .height(300.dp)
                                .weight(2f)
                        ) {
                            Text(
                                text = anime.title,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                            Row {
                                Text(
                                    text = "Total Episode: ",
                                    fontWeight = FontWeight.Medium,
                                )
                                Text(text = anime.episode)
                            }

                            Text(
                                text = "Airing Date: ",
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                            )

                            Text(text = anime.airingDate)
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .wrapContentHeight()
                            .verticalScroll(state)
                    ) {
                        Text(
                            text = "Synopsis: ",
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.wrapContentHeight()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = anime.desc,
                            modifier = Modifier.wrapContentHeight())
                    }
                }
            }
        }
    }

    if (showDialogDelete) {
        AlertDialog(
            onDismissRequest = { showDialogDelete = false },
            title = {
                Text("Confirmation")
            },
            text = {
                Text("Are you sure to delete $title?")
            },
            dismissButton = {
                Button(
                    onClick = { showDialogDelete = false },
                ) {
                    Text("No")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        animeListViewModel.deleteAnime(title)
                        activity?.finish()
                        context.startActivity(Intent(context, MainActivity::class.java))
                    },
                ) {
                    Text("Yes")
                }
            },
        )
    }
}

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    AnimeListTheme {
        DetailAnime()
    }
}