package com.jd.harrypotterapp.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jd.harrypotterapp.core.ViewModelFactory
import com.jd.harrypotterapp.data.entity.CharacterEntity
import com.jd.harrypotterapp.databinding.HomeFragmentBinding
import com.jd.harrypotterapp.presentation.di.DaggerHomeFragmentComponent
import com.jd.harrypotterapp.presentation.item.CharacterListEntity
import kotlinx.coroutines.*
import java.net.URL
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private lateinit var binding: HomeFragmentBinding

    private var characterList = mutableStateListOf<CharacterListEntity>()
    private var loadingHolder = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater).apply {
            composeView.setContent {
                MaterialTheme {
                    HomeComposable(
                        characterList,
                        loadingHolder
                    )
                }
            }
        }

        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.startDataRetrieval()

        viewModel.dataLoadedEvent.observe(
            viewLifecycleOwner
        ) {
            GlobalScope.launch {
                showData(it)
            }
        }
    }

    private fun showData(data: List<CharacterEntity>) {
        handleLoadingIndicator(true)

        characterList.addAll(data.map {
            CharacterListEntity(entity = it, image = getImage(it.image))
        })

        handleLoadingIndicator(false)
    }

    private fun getImage(image: String): Bitmap {
        return try {
            if (image.isNotBlank()) {
                BitmapFactory.decodeStream(
                    URL(image).openConnection().getInputStream()
                )
            } else {
                BitmapFactory.decodeStream(
                    URL("https://upload.wikimedia.org/wikipedia/en/5/5a/Black_question_mark.png").openConnection()
                        .getInputStream()
                )
            }
        } catch (e: Exception) {
            BitmapFactory.decodeStream(
                URL("https://upload.wikimedia.org/wikipedia/en/5/5a/Black_question_mark.png").openConnection()
                    .getInputStream()
            )
        }
    }

    private fun handleLoadingIndicator(isLoading: Boolean) {
        loadingHolder.value = isLoading
    }

    private fun injectDependencies() {
        DaggerHomeFragmentComponent
            .builder()
            .build()
            .inject(this)
    }
}