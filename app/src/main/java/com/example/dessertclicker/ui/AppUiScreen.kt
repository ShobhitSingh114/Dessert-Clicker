package com.example.dessertclicker.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.dessertclicker.DessertClickerAppBar
//import com.example.dessertclicker.DessertClickerScreen
//import com.example.dessertclicker.DessertsSoldInfo
import com.example.dessertclicker.R
//import com.example.dessertclicker.RevenueInfo
//import com.example.dessertclicker.TransactionInfo
import com.example.dessertclicker.model.Dessert


@Composable
fun Abc(
    viewModel: AppViewModel = viewModel()
) {
    val appUiState by viewModel.dessertUiState.collectAsState()
    DessertClickerApp(
        uiState = appUiState,
        onDessertClicked = viewModel::onDessertClicked
    )
}


@Composable
private fun DessertClickerApp(
//    desserts: List<Dessert>,
    uiState: AppUiState,
    onDessertClicked: () -> Unit,
    viewModel: AppViewModel = viewModel()
) {


//    var revenue by rememberSaveable { mutableStateOf(0) }
//    var dessertsSold by rememberSaveable { mutableStateOf(0) }
//
//    val currentDessertIndex by rememberSaveable { mutableStateOf(0) }
//
//    var currentDessertPrice by rememberSaveable {
//        mutableStateOf(desserts[currentDessertIndex].price)
//    }
//    var currentDessertImageId by rememberSaveable {
//        mutableStateOf(desserts[currentDessertIndex].imageId)

    Scaffold(
        topBar = {
            val intentContext = LocalContext.current
            DessertClickerAppBar(
                onShareButtonClicked = {
                    viewModel.shareSoldDessertsInformation(
                        intentContext = intentContext,
                        dessertsSold = uiState.dessertsSold,
                        revenue = uiState.revenue
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    ) { contentPadding ->
        DessertClickerScreen(
            revenue = uiState.revenue,
            dessertsSold = uiState.dessertsSold,
            dessertImageId = uiState.currentDessertImageId,
            onDessertClicked = onDessertClicked,
//            {
//                // Update the revenue
//                appUiState.revenue += appUiState.currentDessertPrice
//                appUiState.dessertsSold++
//
//                // Show the next dessert
//                val dessertToShow = viewModel.determineDessertToShow(desserts, appUiState.dessertsSold)
//                appUiState.currentDessertImageId = dessertToShow.imageId
//                appUiState.currentDessertPrice = dessertToShow.price
//            }
            modifier = Modifier.padding(contentPadding)
        )
    }
}


@Composable
private fun DessertClickerAppBar(
    onShareButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium)),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
        )
        IconButton(
            onClick = onShareButtonClicked,
            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium)),
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(R.string.share),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
@Composable
fun DessertClickerScreen(
    revenue: Int,
    dessertsSold: Int,
    @DrawableRes dessertImageId: Int,
    onDessertClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.bakery_back),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(dessertImageId),
                    contentDescription = null,
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.image_size))
                        .height(dimensionResource(R.dimen.image_size))
                        .align(Alignment.Center)
                        .clickable { onDessertClicked() },
                    contentScale = ContentScale.Crop,
                )
            }
            TransactionInfo(
                revenue = revenue,
                dessertsSold = dessertsSold,
                modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
            )
        }
    }
}

@Composable
private fun TransactionInfo(
    revenue: Int,
    dessertsSold: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DessertsSoldInfo(
            dessertsSold = dessertsSold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
        RevenueInfo(
            revenue = revenue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
private fun RevenueInfo(revenue: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.total_revenue),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = "$${revenue}",
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun DessertsSoldInfo(dessertsSold: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.dessert_sold),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = dessertsSold.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

//
///**
// * Determine which dessert to show.
// */
//fun determineDessertToShow(
//    desserts: List<Dessert>,
//    dessertsSold: Int
//): Dessert {
//    var dessertToShow = desserts.first()
//    for (dessert in desserts) {
//        if (dessertsSold >= dessert.startProductionAmount) {
//            dessertToShow = dessert
//        } else {
//            // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
//            // you'll start producing more expensive desserts as determined by startProductionAmount
//            // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
//            // than the amount sold.
//            break
//        }
//    }
//
//    return dessertToShow
//}
//
///**
// * Share desserts sold information using ACTION_SEND intent
// */
//fun shareSoldDessertsInformation(intentContext: Context, dessertsSold: Int, revenue: Int) {
//    val sendIntent = Intent().apply {
//        action = Intent.ACTION_SEND
//        putExtra(
//            Intent.EXTRA_TEXT,
//            intentContext.getString(R.string.share_text, dessertsSold, revenue)
//        )
//        type = "text/plain"
//    }
//
//    val shareIntent = Intent.createChooser(sendIntent, null)
//
//    try {
//        ContextCompat.startActivity(intentContext, shareIntent, null)
//    } catch (e: ActivityNotFoundException) {
//        Toast.makeText(
//            intentContext,
//            intentContext.getString(R.string.sharing_not_available),
//            Toast.LENGTH_LONG
//        ).show()
//    }
//}



