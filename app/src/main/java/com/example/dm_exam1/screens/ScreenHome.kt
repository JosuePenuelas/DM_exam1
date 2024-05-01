package com.example.dm_exam1.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dm_exam1.R
import com.example.dm_exam1.data.DataProvider
import com.example.dm_exam1.data.GardenPlant
import com.example.dm_exam1.data.Plant

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHome(
    plants: List<Plant>,
    gardenPlants: List<GardenPlant>,
    onShowClick: (Plant) -> Unit = {},
    context: Context,
    dataProvider: DataProvider
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Sunflower")
                },
                actions = {
                    IconButton(onClick = { dataProvider.deleteDataGarden(context) }) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "agregar al jardin"
                    )
                }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(top = 60.dp)) {
            ScreenHomeBody(plants, gardenPlants, onShowClick, dataProvider)
        }
    }
}

@Composable
fun ScreenHomeBody(
    plants: List<Plant>,
    gardenPlants: List<GardenPlant>,
    onShowClick: (Plant) -> Unit = {},
    dataProvider: DataProvider
) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf(
        TabInfo(title = "My Garden", icon = R.drawable.flower_icon),
        TabInfo(title = "Plant List", icon = R.drawable.plant_icon)
    )

    Column(modifier = Modifier
        .fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, tabInfo ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        Image(
                            painter = painterResource(tabInfo.icon),
                            contentDescription = "iconos de los tab",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    },
                    text = { Text(tabInfo.title) }
                )
            }
        }
        when (tabIndex) {
            0 -> 
                if(gardenPlants == emptyList<GardenPlant>()) {
                    Column {
                        Text(
                            text = "Tú jardin esta vacío",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(100.dp, 50.dp)
                        )
                        Button(
                            onClick = {tabIndex = 1},
                            modifier = Modifier
                            .wrapContentHeight()
                                .align(Alignment.CenterHorizontally)) {
                            Text(text = "Agregar Planta")
                        }   
                    }
                }else {
                    ScreenMyGarden(gardenPlants, onShowClick, dataProvider)
                }
            1 -> ScreenPlantList(plants, onShowClick)
        }
    }
}

data class TabInfo(val title: String, val icon: Int)