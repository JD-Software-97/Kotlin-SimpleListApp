package com.jd.harrypotterapp.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jd.harrypotterapp.data.entity.CharacterEntity
import com.jd.harrypotterapp.presentation.item.CharacterListEntity
import java.util.Locale

@Composable
fun HomeComposable(entityList: List<CharacterListEntity>, isLoading: MutableState<Boolean>) {

    val data by remember { mutableStateOf(entityList) }
    val loading by remember { mutableStateOf(isLoading) }

    Scaffold(
        content = { padding ->
            if (loading.value) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(data) { character ->
                    CharacterItem(character.entity, character.image)
                }
            }
        }
    )
}

@Composable
fun CharacterItem(characterEntity: CharacterEntity, image: Bitmap) {
    Card(
        modifier = Modifier
            .padding(2.dp, 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Fit,
            )
            Column {
                Text(
                    text = characterEntity.name,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = characterEntity.species.capitalize(Locale.getDefault()),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = characterEntity.dateOfBirth.toString(),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}
