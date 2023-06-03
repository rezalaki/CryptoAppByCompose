package com.rezalaki.cryptobycompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rezalaki.cryptobycompose.ui.screens.main.MainViewModel
import com.rezalaki.cryptobycompose.ui.theme.CryptoByComposeTheme
import com.rezalaki.cryptobycompose.ui.theme.colorBackground
import com.rezalaki.cryptobycompose.ui.theme.colorBlack
import com.rezalaki.cryptobycompose.ui.theme.colorGrayDark
import com.rezalaki.cryptobycompose.ui.theme.colorGreen
import com.rezalaki.cryptobycompose.ui.theme.colorRed
import com.rezalaki.cryptobycompose.ui.theme.colorWhite
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.*

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
    viewModel: MainViewModel = viewModel()
) {
    viewModel.callApi()


    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorBackground)
        ) {
            CryptoItem()
            CryptoItem()
            CryptoItem()
        }
    }
}

@Preview()
@Composable
fun CryptoItem() {
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
                    "Bitcoin",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "24,000",
                        modifier = Modifier
                            .weight(0.3F)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = colorGreen,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "26,000",
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
                        text = "30,000",
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
                    .data("https://assets.coingecko.com/coins/images/1231/large/Blox_Staking_Logo_2.png?1609117544")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_dollar),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(90.dp)
                    .height(90.dp)
                    .padding(all = 2.dp)
                    .background(Color.White)
            )
        }
    }
}