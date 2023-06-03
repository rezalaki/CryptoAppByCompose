package com.rezalaki.cryptobycompose.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezalaki.cryptobycompose.repositorys.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun callApi() = viewModelScope.launch(Dispatchers.IO) {
        val rp = repository.callCryptoList(1)
        if (rp.isSuccessful) {
            rp.body()?.let {
                it.asFlow()
                    .onStart {
                        Log.d("TAGGGG", "callApi: onStart")
                    }
                    .onEach {
                    }
                    .catch {
                        Log.d("TAGGGG", "callApi: catch")
                    }
                    .collect {
                    }
            }
        } else {
            Log.d("TAGGGG", "callApi: != 404")
        }
    }

}