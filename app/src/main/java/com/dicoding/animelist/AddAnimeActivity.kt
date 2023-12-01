package com.dicoding.animelist

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.animelist.database.AnimeEntity
import com.dicoding.animelist.ui.components.AnimeDatePicker
import com.dicoding.animelist.ui.theme.AnimeListTheme
import com.dicoding.animelist.viewmodel.AnimeListViewModel
import com.dicoding.animelist.viewmodel.AnimeViewModelFactory

class AddAnimeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeListTheme {
                AddAnime()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAnime(
    modifier: Modifier = Modifier,
    animeListViewModel: AnimeListViewModel = viewModel(
        factory = AnimeViewModelFactory.getInstance(LocalContext.current)
    )
) {
    val context = LocalContext.current
    val activity = (context as Activity)

    var showPhoto by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    var title: String by remember { mutableStateOf("") }
    var desc: String by remember { mutableStateOf("") }
    var episode: String by remember { mutableStateOf("") }
    var photoUri: Uri? by remember { mutableStateOf(null) }
    var photoLink: String by remember { mutableStateOf("") }
    var airingDate by remember { mutableStateOf("") }

    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
    ) {
        AsyncImage(
            model = if (showPhoto) "$photoUri" else R.drawable.baseline_image_24,
            contentDescription = title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .border(
                    width = 10.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10)
                )
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(10))
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Please, insert an URL with .png or .jpg in the end",
            modifier = Modifier.wrapContentHeight())

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = photoLink,
                label = { Text("Anime Poster Link") },
                onValueChange = {
                    photoLink = it
                    photoUri = if (it.isNotEmpty()) Uri.parse(it) else null
                },
                modifier = Modifier
                    .weight(2f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { showPhoto = true },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                enabled = photoUri != null,
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Preview",
                    color = Color.White
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f, false)
                .verticalScroll(state)
        ) {
            OutlinedTextField(
                value = title,
                label = { Text(stringResource(R.string.type_anime_title)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = {
                    title = it

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)

            )
            OutlinedTextField(
                value = episode,
                label = { Text(stringResource(R.string.type_anime_total_episode)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    episode = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )

            var showDatePicker by remember { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                OutlinedTextField(
                    value = airingDate,
                    label = { Text("Airing Date") },
                    onValueChange = {},
                    enabled = false,
                    modifier = Modifier
                        .weight(2f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text("Select")
                }
            }

            if (showDatePicker) {
                AlertDialog(
                    onDismissRequest = { showDatePicker = false },
                    content = {
                        AnimeDatePicker(
                            onDateSelected = { airingDate = it },
                            onDismiss = { showDatePicker = false })
                    },
                    modifier = Modifier
                        .height(500.dp)
                        .background(
                            color = Color.White, shape = RoundedCornerShape(10)
                        )
                )
            }

            OutlinedTextField(
                value = desc,
                label = { Text(stringResource(R.string.type_anime_synopsis)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = {
                    desc = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .height(150.dp)
            )
        }
        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                if (photoLink.isNotEmpty() && title.isNotEmpty() && airingDate.isNotEmpty() && desc.isNotEmpty()
                ) {
                    val animeList = listOf(AnimeEntity(
                        id = 0,
                        title = title,
                        photo = photoLink,
                        desc = desc,
                        airingDate = airingDate,
                        episode = episode.ifEmpty { "Not end yet" }
                    ))
                    animeListViewModel.addAnime(animeList)
                    context.startActivity(intent)
                    activity.finish()
                } else {
                    showDialog = true
                }
            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(60.dp)
        ) {
            Text(
                text = stringResource(R.string.add_new_anime),
                color = Color.White
            )
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text("Alert")
            },
            text = {
                Text("Please, fill the form first")
            },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                ) {
                    Text("OK")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AddAnimePreview() {
    AnimeListTheme {
        AddAnime()
    }
}

