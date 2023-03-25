package com.example.zeyada

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ZeyadaNumberViewModel: ViewModel() {


    val intentChannel = Channel<ZeyadaIntent>(Channel.UNLIMITED)

    private val viewState = MutableStateFlow<ZeyadaViewState>(ZeyadaViewState.Idle)
    val state: StateFlow<ZeyadaViewState> get() = viewState
    private var number = 0

    init {
        processIntent()
    }

    //Process
    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it){
                    is ZeyadaIntent.AddNumber -> addNumber()
                }
            }
        }
    }

    //Reduce
    private fun addNumber() {
        viewModelScope.launch {
            viewState.value =
                try {
                    ZeyadaViewState.Number(number ++)
                } catch (e: Exception) {
                    ZeyadaViewState.Error(e.message.toString())
                }
        }
    }
}