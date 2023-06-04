package com.rezalaki.cryptobycompose.ui.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rezalaki.cryptobycompose.models.Crypto
import com.rezalaki.cryptobycompose.repositorys.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val cashedCryptoList = MutableLiveData<List<Crypto>>()
    val showCachedData = MutableLiveData(false)

    fun callCryptoApi(): Flow<PagingData<Crypto>> =
        repository.callCryptoList()
            .cachedIn(viewModelScope)
            .catch {
                showCachedData.postValue(true)
            }

    fun fetchCryptoListFromDB() = viewModelScope.launch(Dispatchers.IO) {
        repository.fetchAllFromDatabase()
            .collect{
                cashedCryptoList.postValue(it)
            }
    }

}