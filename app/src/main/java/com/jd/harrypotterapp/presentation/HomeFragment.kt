package com.jd.harrypotterapp.presentation

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jd.harrypotterapp.core.ViewModelFactory
import com.jd.harrypotterapp.data.entity.CharacterEntity
import com.jd.harrypotterapp.databinding.HomeFragmentBinding
import com.jd.harrypotterapp.presentation.di.DaggerHomeFragmentComponent
import com.jd.harrypotterapp.presentation.item.ListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        val adapter = GroupAdapter<GroupieViewHolder>()

        data.forEach { characterEntity ->
            val bitmap = try {
                BitmapFactory.decodeStream(
                    URL(characterEntity.image).openConnection().getInputStream()
                )
            } catch (e: Exception) {
                null
            }

            bitmap?.let { adapter.add(ListItem(characterEntity.name, it)) }
        }

        withContext(Dispatchers.Main) {
            binding.recyclerView.adapter = adapter
        }
    }

    private fun injectDependencies() {
        DaggerHomeFragmentComponent
            .builder()
            .build()
            .inject(this)
    }
}