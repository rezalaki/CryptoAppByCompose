package com.rezalaki.cryptobycompose.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rezalaki.cryptobycompose.R
import com.rezalaki.cryptobycompose.models.Crypto
import com.rezalaki.cryptobycompose.ui.components.LoadingAnimation
import com.rezalaki.cryptobycompose.ui.theme.colorBackground
import com.rezalaki.cryptobycompose.ui.theme.colorBlack
import com.rezalaki.cryptobycompose.ui.theme.colorGrayDark
import com.rezalaki.cryptobycompose.ui.theme.colorGreen
import com.rezalaki.cryptobycompose.ui.theme.colorRed
import com.rezalaki.cryptobycompose.ui.theme.colorWhite
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val cryptoListFromApi = viewModel.callCryptoApi().collectAsLazyPagingItems()

    val showCachedData = viewModel.showCachedData.observeAsState()
    val cryptoListFromCache = viewModel.cashedCryptoList.observeAsState()

    Scaffold(
        modifier = Modifier.background(colorBackground),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        // get data from online API
        if (showCachedData.value == false) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground)
            ) {
                items(items = cryptoListFromApi) { crypto ->
                    CryptoItem(crypto = crypto!!)
                }

                // first loading
                when (cryptoListFromApi.loadState.refresh) {
                    is LoadState.Error -> {
                        viewModel.showCachedData.postValue(true)
                        item {
                            Column(
                                modifier = Modifier
                                    .fillParentMaxSize()
                                    .background(colorBackground),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = "unable to find server",
                                    color = Color.Black,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }

                    is LoadState.Loading -> { // Loading UI
                        item {
                            Column(
                                modifier = Modifier
                                    .fillParentMaxSize()
                                    .background(colorBackground),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                LoadingAnimation()
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    text = "Loading Data From Server...",
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    color = colorBlack
                                )
                            }
                        }
                    }

                    else -> {}
                }

                // when loading more
                when (cryptoListFromApi.loadState.append) {
                    is LoadState.Error -> {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Error while loading more data :/",
                                    fontSize = 16.sp,
                                    color = colorRed,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    is LoadState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                LoadingAnimation(circleSize = 16.dp)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Loading more... ",
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }

                    else -> {}
                }
            }
        } else { // get data from local DB
            viewModel.fetchCryptoListFromDB()
            scope.launch {
                snackbarHostState.showSnackbar(
                    "No internet. current data is from cache"
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground)
            ) {
                cryptoListFromCache.value?.let {
                    if (it.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillParentMaxSize()
                                    .background(colorBackground),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = "No Cashed Data :/",
                                    fontSize = 24.sp,
                                    color = colorBlack,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        items(cryptoListFromCache.value!!) { crypto ->
                            CryptoItem(crypto = crypto)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CryptoItem(crypto: Crypto) {
    // fix some null values via my fake data --> start
    val currentPrice = if (crypto.currentPrice == null) {
        "5.05"
    } else if (crypto.currentPrice!!.toString().length > 5) {
        crypto.currentPrice!!.toString().take(6)
    } else {
        crypto.currentPrice!!.toString()
    }
    val low24 = if (crypto.low24h == null) {
        "2.02"
    } else if (crypto.low24h!!.toString().length > 5) {
        crypto.low24h!!.toString().take(6)
    } else {
        crypto.low24h!!.toString()
    }
    val high24 = if (crypto.high24h == null) {
        "8.08"
    } else if (crypto.high24h!!.toString().length > 5) {
        crypto.currentPrice!!.toString().take(6)
    } else {
        crypto.currentPrice!!.toString()
    }
    // fix some null values via my fake data --> end

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)

    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .padding(start = 32.dp, end = 16.dp, top = 32.dp, bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorWhite
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    crypto.name.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorBlack
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = low24,
                        modifier = Modifier
                            .weight(0.3F)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = colorGreen,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = currentPrice,
                        modifier = Modifier
                            .weight(0.4F)
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            ),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = colorGrayDark,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = high24,
                        modifier = Modifier
                            .weight(0.3F)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = colorRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
        Box(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(crypto.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_dollar),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(90.dp)
                    .height(90.dp)
                    .background(colorWhite)
            )
        }
    }
}