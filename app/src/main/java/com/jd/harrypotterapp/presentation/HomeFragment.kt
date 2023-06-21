package com.jd.harrypotterapp.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jd.harrypotterapp.core.ViewModelFactory
import com.jd.harrypotterapp.data.entity.CharacterEntity
import com.jd.harrypotterapp.databinding.HomeFragmentBinding
import com.jd.harrypotterapp.presentation.di.DaggerHomeFragmentComponent
import com.jd.harrypotterapp.presentation.item.ListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.*
import java.net.URL
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private lateinit var binding: HomeFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
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

    private suspend fun showData(data: List<CharacterEntity>) {
        handleLoadingIndicator(true)

        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.addAll(data.map {
            ListItem(
                characterEntity = it,
                image = getImage(it.image)
            )
        })

        withContext(Dispatchers.Main) {
            binding.recyclerView.adapter = adapter
        }

        handleLoadingIndicator(false)
    }

    private fun getImage(image: String): Bitmap? {
        return try {
            if(image.isNotBlank()) {
                BitmapFactory.decodeStream(
                    URL(image).openConnection().getInputStream()
                )
            }else{
                BitmapFactory.decodeStream(
                    URL("https://upload.wikimedia.org/wikipedia/en/5/5a/Black_question_mark.png").openConnection().getInputStream()
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun handleLoadingIndicator(isLoading: Boolean) {
        withContext(Dispatchers.Main) {
            binding.progressCircular.isVisible = isLoading
        }
    }

    private fun injectDependencies() {
        DaggerHomeFragmentComponent
            .builder()
            .build()
            .inject(this)
    }
}