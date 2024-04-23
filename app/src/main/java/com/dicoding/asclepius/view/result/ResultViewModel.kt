package com.dicoding.asclepius.view.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.ClassificationResult
import com.dicoding.asclepius.data.repository.ResultRepository
import kotlinx.coroutines.launch

class ResultViewModel(private val repository: ResultRepository) : ViewModel() {
    fun insert(classificationResult: ClassificationResult) {
        viewModelScope.launch { repository.insert(classificationResult) }
    }

    fun delete(classificationResult: ClassificationResult) {
        viewModelScope.launch { repository.delete(classificationResult) }
    }
}