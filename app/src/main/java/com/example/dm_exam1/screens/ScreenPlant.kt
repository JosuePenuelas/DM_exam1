package com.example.dm_exam1.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.dm_exam1.data.DataProvider
import com.example.dm_exam1.data.Plant
import android.text.Html
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun ScreenPlant(
    plant: Plant,
    onBackClick: () -> Unit = {},
    context: Context,
    dataProvider: DataProvider
) {
    var isButtonEnabled by remember { mutableStateOf(true) }
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(4.dp)
        ) {
            Box {
                AsyncImage(
                    model = plant.imageUrl,
                    contentDescription = "imagen de la flor ${plant.name}"
                )
                FloatingActionButton(
                    onClick = { onBackClick() }, modifier = Modifier
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "boton para regresar",
                    )
                }
            }

            Row {
                //boton para agregar al jardin
                if (!dataProvider.searchGardenPlant(plant)) {
                    FloatingActionButton(
                        onClick = {
                            dataProvider.saveDataGarden(context, plant)
                            isButtonEnabled = false
                            onBackClick()
                        },
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "agregar al jardin"
                        )
                    }
                } else {
                    isButtonEnabled = true
                }

                //boton compartir
                FloatingActionButton(
                    onClick = { sharePlant(context, plant) },
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Share,
                        contentDescription = "compartir información de la planta",
                    )
                }
            }

            //nombre
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        top = 25.dp
                    )
            ) {
                Text(
                    text = plant.name,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                )
            }

            //Frecuencia de riego
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        top = 25.dp
                    )
            ) {
                Text(
                    text = "Frecuencia de Riego",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "cada " + plant.wateringInterval.toString() + " días",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                )
            }

            //descripcion
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        top = 25.dp
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = buildAnnotatedString { append(Html.fromHtml(plant.description)) },
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

fun sharePlant(context: Context, plant: Plant) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Información de planta")
        putExtra(
            Intent.EXTRA_TEXT, "Nombre: ${plant.name}\n" +
                    "Frecuencia de riego: ${plant.wateringInterval}\n" +
                    "Descripción: ${plant.description}"
        )
    }
    context.startActivity(Intent.createChooser(shareIntent, "Compartir vía"))
}