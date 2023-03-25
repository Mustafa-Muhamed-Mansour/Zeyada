package com.example.zeyada

sealed class ZeyadaViewState {

    //Idle
    object Idle: ZeyadaViewState()
    //Number
    data class Number(val num: Int): ZeyadaViewState()
    //Error
    data class Error(val error: String): ZeyadaViewState()
}