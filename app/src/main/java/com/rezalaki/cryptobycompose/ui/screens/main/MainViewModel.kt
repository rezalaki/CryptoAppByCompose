package com.rezalaki.cryptobycompose.ui.screens.main

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.rezalaki.cryptobycompose.models.Crypto
import com.rezalaki.cryptobycompose.network.ApiPagingSource
import com.rezalaki.cryptobycompose.repositorys.MainRepository
import com.rezalaki.cryptobycompose.utils.Constants.API_PAGING_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val showProgressbar = MutableLiveData(true)
    val errorCallingApi = MutableLiveData<Boolean>()

    fun callCryptoApi(): Flow<PagingData<Crypto>>? =
        repository.callCryptoList().cachedIn(viewModelScope)
//            .onCompletion {
//                showProgressbar.postValue(false)
//            }
            .onEach {
                it.map { crypto->
                    Log.d("TAGGGG", "callCryptoApi: map -> $crypto")
                }
            }
            .catch {
//                errorCallingApi.postValue(true)
//                showProgressbar.postValue(false)
            }


}