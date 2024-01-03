package com.example.airlinespractical

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.example.airlinespractical.databinding.ActivityMainBinding
import com.example.airlinespractical.paging.AirlinesPagingAdapter
import com.example.airlinespractical.paging.AirlinesViewModel
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalPagingApi::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val airlinesViewModel: AirlinesViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // airlinesViewModel = ViewModelProvider(this)[AirlinesViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.airlinesList)
        val adapter = AirlinesPagingAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        binding.airlinesSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                airlinesViewModel.updateFilter(newText.toString())
                return true
            }
        })

        airlinesViewModel.list.observe(this) {
            adapter.submitData(lifecycle, it)
        }

        airlinesViewModel.filteredPagingDataLiveData.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}