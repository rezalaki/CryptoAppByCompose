package com.rezalaki.cryptobycompose.ui.screens.main

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rezalaki.cryptobycompose.models.Crypto
import com.rezalaki.cryptobycompose.repositorys.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    var data = MutableLiveData<List<Crypto>?>(emptyList())
    val showProgressbar = MutableLiveData(true)

    fun callApi() = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TAGGGG", "callApi: ")
        val rp = repository.callCryptoList(1)
        if (rp.isSuccessful) {
            rp.body()?.let {
                if (it.isNotEmpty()) {
                    showProgressbar.postValue(false)
                    data.postValue(it)
                    Log.d("TAGGGG", "callApi: OK")
                }
            }

        } else {
            Log.d("TAGGGG", "callApi: Error")

            data.postValue(null)
        }
    }

}