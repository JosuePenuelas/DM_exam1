package com.example.dm_exam1.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.dm_exam1.data.DataProvider
import com.example.dm_exam1.data.GardenPlant
import com.example.dm_exam1.data.Plant

@Composable
fun ScreenMyGarden(
    gardenPlant: List<GardenPlant>,
    onShowClick: (Plant) -> Unit = {},
    dataProvider: DataProvider
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(gardenPlant) { plant ->
                BodyMyGarden(plant, onShowClick, dataProvider)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyMyGarden(plant: GardenPlant, onShowClick: (Plant) -> Unit = {}, dataProvider: DataProvider) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(300.dp),
        onClick = { dataProvider.searchPlant(plant)?.let { onShowClick(it) } },
        ) {

        Column (modifier = Modifier .verticalScroll(rememberScrollState())) {
            AsyncImage(
                model = plant.image,
                contentDescription = "imagen de la flor ${plant.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )

            //nombre
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 25.dp)
            ) {
                Text(
                    text = plant.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentHeight()
                )
            }

            //fecha
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 25.dp)
            ) {
                Text(
                    text = "Plantado",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentHeight()
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = plant.plantingDate,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentHeight()
                )
            }

            //frecuencia de riego
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 25.dp)
            ) {
                Text(
                    text = "Riego",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentHeight()
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "cada " + plant.wateringInterval.toString() + " días",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentHeight()
                )
            }
        }
    }
}