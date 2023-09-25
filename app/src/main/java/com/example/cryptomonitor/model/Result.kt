package com.example.cryptomonitor.model

sealed interface Result{
    object Success : Result
    object Error : Result
}
