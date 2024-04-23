package com.dicoding.asclepius.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.ClassificationResult
import com.dicoding.asclepius.data.repository.ResultRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: ResultRepository) : ViewModel() {
    private val _results = MutableLiveData<List<ClassificationResult>>()
    val results: LiveData<List<ClassificationResult>> = _results

    init {
        loadResults()
    }

    private fun loadResults() {
        viewModelScope.launch {
            repository.getAll().observeForever {
                _results.postValue(it)
            }
        }
    }
}