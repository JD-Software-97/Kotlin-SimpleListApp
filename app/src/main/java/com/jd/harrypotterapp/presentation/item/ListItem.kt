package com.jd.harrypotterapp.presentation.item

import android.graphics.Bitmap
import android.view.View
import com.jd.harrypotterapp.R
import com.jd.harrypotterapp.data.entity.CharacterEntity
import com.jd.harrypotterapp.databinding.HomeLayoutBinding
import com.xwray.groupie.viewbinding.BindableItem

class ListItem(private val characterEntity: CharacterEntity, private val image: Bitmap) :
    BindableItem<HomeLayoutBinding>() {

    override fun getLayout(): Int = R.layout.home_layout

    override fun initializeViewBinding(view: View): HomeLayoutBinding {
        return HomeLayoutBinding.bind(view)
    }

    override fun bind(viewBinding: HomeLayoutBinding, position: Int) {
        viewBinding.bind()
    }

    private fun HomeLayoutBinding.bind() {
        characterImg.setImageBitmap(image)
        nameTxt.text = characterEntity.name
        speciesText.text = characterEntity.species
        birthText.text = characterEntity.dateOfBirth
    }
}