package com.example.dm_exam1.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.dm_exam1.PlantActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Date

class DataProvider {
    var plants: List<Plant> = emptyList()
    var gardenPlants: MutableList<GardenPlant> = mutableListOf()

    fun loadDataPlant(context: Context, fileName: String) {
        val plantsJsonString = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Array<Plant>::class.java)
        plants = adapter.fromJson(plantsJsonString)?.toList() ?: emptyList()
    }

    fun loadDataGarden(context: Context) {
        try {
            // Obtener el directorio de archivos internos
            val file = File(context.filesDir, "garden.json")

            if (file.exists()) {
                // Abrir el archivo para lectura
                val fileInputStream = FileInputStream(file)
                val reader = BufferedReader(InputStreamReader(fileInputStream))

                // Leer los datos del archivo
                val gardenPlantsJson = reader.use { it.readText() }

                // Convertir el JSON a una lista de GardenPlant
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val type = Types.newParameterizedType(List::class.java, GardenPlant::class.java)
                val adapter: JsonAdapter<List<GardenPlant>> = moshi.adapter(type)
                gardenPlants =
                    adapter.fromJson(gardenPlantsJson)?.toMutableList() ?: mutableListOf()

                // Cerrar el lector
                reader.close()
            } else {
                // El archivo no existe, cargar una lista vacía
                gardenPlants = mutableListOf()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun saveDataGarden(context: Context, plant: Plant) {
        val gardenPlant =
            GardenPlant(
                plant.imageUrl,
                plant.name,
                "${Date()}",
                plant.wateringInterval)
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, GardenPlant::class.java)
        val adapter: JsonAdapter<List<GardenPlant>> = moshi.adapter(type)

        gardenPlants.add(gardenPlant)

        try {
            // Obtener el directorio de archivos internos
            val file = File(context.filesDir, "garden.json")

            // Crear o abrir un archivo en el directorio de archivos internos
            val fileOutputStream = FileOutputStream(file)
            val writer = BufferedWriter(OutputStreamWriter(fileOutputStream))

            // Convertir la lista de GardenPlant a JSON
            val gardenPlantsJson = adapter.toJson(gardenPlants)

            // Escribir el JSON en el archivo
            writer.write(gardenPlantsJson)
            writer.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        loadDataGarden(context)
    }

    fun deleteDataGarden(context: Context) {
        try {
            val file = File(context.filesDir, "garden.json")
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        loadDataGarden(context)
        val intent = (context as Activity).intent
        context.finish()
        context.startActivity(intent)
    }

    fun searchGardenPlant(plant: Plant): Boolean {
        var boolean: Boolean = false
        gardenPlants.forEach { gardenPlant ->
            if (gardenPlant.name == plant.name){
                boolean = true
            }
        }
        return boolean
    }

    fun searchPlant(plant: GardenPlant): Plant? {
        var plant2: Plant? = null
        plants.forEach { plantInList ->
            if (plantInList.name == plant.name) {
                plant2 = plantInList
            }
        }
        return plant2
    }

}

object DataProviderSingleton {
    val instance: DataProvider by lazy { DataProvider() }
}