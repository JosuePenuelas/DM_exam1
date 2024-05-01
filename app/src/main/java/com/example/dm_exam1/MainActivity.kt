package com.example.dm_exam1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.dm_exam1.data.DataProviderSingleton
import com.example.dm_exam1.data.GardenPlant
import com.example.dm_exam1.data.Plant
import com.example.dm_exam1.screens.ScreenHome
import com.example.dm_exam1.ui.theme.DM_exam1Theme


class MainActivity : ComponentActivity() {
    val dataProvider = DataProviderSingleton.instance
    val state: MutableState<List<Plant>> = mutableStateOf(emptyList())
    val state2: MutableState<List<GardenPlant>> = mutableStateOf(emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DM_exam1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    dataProvider.loadDataPlant(this, "plants.json")
                    dataProvider.loadDataGarden(this)
                    state.value = dataProvider.plants
                    state2.value = dataProvider.gardenPlants
                    ScreenHome(
                        plants = state.value,
                        gardenPlants = state2.value,
                        onShowClick = ::goToDetailScreen,
                        this,
                        dataProvider
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dataProvider.loadDataGarden(this)
        state2.value = dataProvider.gardenPlants
    }

    fun goToDetailScreen(plant: Plant) {
        val intent = Intent(this@MainActivity, PlantActivity::class.java)
        intent.putExtra("PLANT", plant)
        startActivity(intent)
        Log.d("ScreenPlant", "Show passed to DetailActivity: $plant")
    }
}
