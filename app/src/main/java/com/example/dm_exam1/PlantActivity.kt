package com.example.dm_exam1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.dm_exam1.data.DataProviderSingleton
import com.example.dm_exam1.data.Plant
import com.example.dm_exam1.screens.ScreenPlant
import com.example.dm_exam1.ui.theme.DM_exam1Theme

class PlantActivity : ComponentActivity() {
    val dataProvider = DataProviderSingleton.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DM_exam1Theme {
                Surface {
                    val plant = intent.getParcelableExtra<Plant>("PLANT")
                    ScreenPlant(plant!!,
                        onBackClick = { finish() },
                        this,
                        dataProvider)
                }
            }
        }
    }
}