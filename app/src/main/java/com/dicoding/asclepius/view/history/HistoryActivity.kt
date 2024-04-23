package com.dicoding.asclepius.view.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.local.ClassificationResult
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.util.ViewModelFactory

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeLiveData()

        supportActionBar?.hide()
    }

    private fun observeLiveData() {
        viewModel.results.observe(this) { setResults(it) }
    }

    private fun setResults(results: List<ClassificationResult>) {
        val adapter = ResultAdapter()
        adapter.submitList(results)
        binding.rvHistory.adapter = adapter
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvHistory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvHistory.addItemDecoration(itemDecoration)
    }
}