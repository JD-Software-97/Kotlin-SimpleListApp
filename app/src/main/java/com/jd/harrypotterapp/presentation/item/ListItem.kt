package com.jd.harrypotterapp.presentation.item

import android.graphics.Bitmap
import android.view.View
import com.jd.harrypotterapp.R
import com.jd.harrypotterapp.data.entity.CharacterEntity
import com.jd.harrypotterapp.databinding.HomeLayoutBinding
import com.xwray.groupie.viewbinding.BindableItem

class ListItem(private val characterEntity: CharacterEntity, private val image: Bitmap?) :
    BindableItem<HomeLayoutBinding>() {

    override fun getLayout(): Int = R.layout.home_layout

    override fun initializeViewBinding(view: View): HomeLayoutBinding {
        return HomeLayoutBinding.bind(view)
    }

    override fun bind(viewBinding: HomeLayoutBinding, position: Int) {
        viewBinding.bind()
    }

    private fun HomeLayoutBinding.bind() {
        image?.let { characterImg.setImageBitmap(it) }
        nameTxt.text =
            characterEntity.name.ifBlank { root.resources.getString(R.string.unknown_name) }
        speciesText.text =
            characterEntity.species.capitalize()
                .ifBlank { root.resources.getString(R.string.unknown_species) }
        birthText.text =
            characterEntity.dateOfBirth?.ifBlank { root.resources.getString(R.string.unknown_dob) } ?: root.resources.getString(R.string.unknown_dob)
    }
}