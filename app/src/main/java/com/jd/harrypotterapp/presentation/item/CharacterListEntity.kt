package com.jd.harrypotterapp.presentation.item

import android.graphics.Bitmap
import com.jd.harrypotterapp.data.entity.CharacterEntity

data class CharacterListEntity(
    val entity: CharacterEntity,
    val image: Bitmap
)