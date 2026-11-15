package com.example.appnavegate.ui

import androidx.lifecycle.ViewModel
import com.example.appnavegate.data.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigateViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onScreenChanged() {
        _uiState.update {it.copy(counter = it.counter + 1)}
    }
}
