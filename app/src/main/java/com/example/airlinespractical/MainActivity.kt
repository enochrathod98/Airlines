package com.example.airlinespractical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.example.airlinespractical.paging.AirlinesPagingAdapter
import com.example.airlinespractical.paging.AirlinesViewModel
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalPagingApi::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

     val airlinesViewModel: AirlinesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // airlinesViewModel = ViewModelProvider(this)[AirlinesViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.airlinesList)
        val adapter = AirlinesPagingAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        airlinesViewModel.list.observe(this) { it ->
            adapter.submitData(lifecycle, it)
        }
    }
}