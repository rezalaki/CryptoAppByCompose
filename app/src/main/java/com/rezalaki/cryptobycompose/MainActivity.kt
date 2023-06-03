package com.rezalaki.cryptobycompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.*
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rezalaki.cryptobycompose.models.Crypto
import com.rezalaki.cryptobycompose.ui.components.LoadingAnimation
import com.rezalaki.cryptobycompose.ui.screens.main.MainViewModel
import com.rezalaki.cryptobycompose.ui.theme.CryptoByComposeTheme
import com.rezalaki.cryptobycompose.ui.theme.Purple40
import com.rezalaki.cryptobycompose.ui.theme.colorBackground
import com.rezalaki.cryptobycompose.ui.theme.colorBlack
import com.rezalaki.cryptobycompose.ui.theme.colorGrayDark
import com.rezalaki.cryptobycompose.ui.theme.colorGreen
import com.rezalaki.cryptobycompose.ui.theme.colorRed
import com.rezalaki.cryptobycompose.ui.theme.colorWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CryptoByComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun Screen(
    viewModel: MainViewModel = hiltViewModel()
) {
    viewModel.callApi()
    val dataList = viewModel.data.observeAsState()
    val showProgressBar = viewModel.showProgressbar.observeAsState()

    Scaffold(
        modifier = Modifier.background(colorBackground)
    ) {
        if (showProgressBar.value!!) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingAnimation()
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Loading...", fontSize = 22.sp, color = Purple40,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            if(dataList.value == null){
                Text(
                    text = "error in receiving date ...", fontSize = 32.sp, color = Purple40,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier.background(colorBackground)
                ) {
                    dataList.value?.let {
                        items(it) { crypto ->
                            CryptoItem(crypto)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun CryptoItem(crypto: Crypto) {
    val currentPrice = if (crypto.currentPrice!!.toString().length > 5) {
        crypto.currentPrice!!.toString().take(6)
    } else {
        crypto.currentPrice!!.toString()
    }
    val low24 = if (crypto.low24h!!.toString().length > 5) {
        crypto.low24h!!.toString().take(6)
    } else {
        crypto.low24h!!.toString()
    }
    val high24 = if (crypto.high24h!!.toString().length > 5) {
        crypto.currentPrice!!.toString().take(6)
    } else {
        crypto.currentPrice!!.toString()
    }
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
                        text = crypto.low24h.toString(),
                        modifier = Modifier
                            .weight(0.3F)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = colorGreen,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = crypto.currentPrice.toString(),
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
                        text = crypto.high24h.toString(),
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