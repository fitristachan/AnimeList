package com.dicoding.animelist.ui.components

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.animelist.DetailAnimeActivity

@Composable
fun AnimeCard(
    title: String,
    desc: String,
    photo: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val intent = Intent(context, DetailAnimeActivity::class.java)
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                intent.putExtra("title", title)
                context.startActivity(intent)
            })
    ){
        AsyncImage(
            model = photo,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(width = 100.dp, height = 200.dp)
                .clip(RoundedCornerShape(15))
                .border(
                    width = 10.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(15)
                )
        )

        Column {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )

            Text(
                text = desc,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 5
            )
        }
    }

}

