package com.dicoding.animelist

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.animelist.database.AnimeData
import com.dicoding.animelist.ui.components.AnimeCard
import com.dicoding.animelist.ui.components.Search
import com.dicoding.animelist.ui.theme.AnimeListTheme
import com.dicoding.animelist.viewmodel.AnimeListViewModel
import com.dicoding.animelist.viewmodel.AnimeViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                AnimeListTheme {
                    MainPage()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    animeListViewModel: AnimeListViewModel = viewModel(
        factory = AnimeViewModelFactory.getInstance(LocalContext.current)
    )
) {
    val animeList by animeListViewModel.getAllAnime.collectAsState()
    LaunchedEffect(animeListViewModel) {
        animeListViewModel.loadAnimeList()
    }

    val query by animeListViewModel.query

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                actions = {
                    val intent = Intent(context, ProfileActivity::class.java)
                    IconButton(
                        onClick = { context.startActivity(intent) },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(R.string.my_profile),
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            val intent = Intent(context, AddAnimeActivity::class.java)
            FloatingActionButton(
                onClick = {
                    context.startActivity(intent)
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_new_anime),
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
            LazyColumn(
                userScrollEnabled = true,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                item {
                    Search(
                        query = query,
                        onQueryChange = animeListViewModel::loadSearchAnime,
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                    )
                }
                if (animeList.isNotEmpty()) {
                    items(animeList, key = { it.id }) { anime ->
                        AnimeCard(
                            title = anime.title,
                            photo = anime.photo,
                            desc = anime.desc,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                    }
                } else if (animeList.isEmpty() && query.isEmpty()) {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize(),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.wrapContentSize()
                            ) {
                                AsyncImage(
                                    model = R.drawable.confused_icon,
                                    contentDescription = stringResource(R.string.icon_confused),
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .size(150.dp)
                                        .padding(vertical = 8.dp)
                                )
                                Text(
                                    text = stringResource(R.string.null_animelist),
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                )
                                Button(
                                    onClick = {
                                        animeListViewModel.addAnime(AnimeData.animes)
                                        context.findActivity()?.finish()
                                        context.startActivity(
                                            Intent(
                                                context,
                                                MainActivity::class.java
                                            )
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Generate Dummy",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                } else if (animeList.isEmpty() && query.isNotEmpty()) {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize(),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.wrapContentSize()
                            ) {
                                AsyncImage(
                                    model = R.drawable.confused_icon,
                                    contentDescription = stringResource(R.string.icon_confused),
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .size(150.dp)
                                        .padding(vertical = 8.dp)
                                )
                                Text(
                                    text = stringResource(R.string.hmm_we_cannot_find_that_title),
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun MainPagePreview() {
    AnimeListTheme {
        MainPage()
    }
}