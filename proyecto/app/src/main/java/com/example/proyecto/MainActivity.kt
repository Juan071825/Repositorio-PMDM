package com.example.proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto.data.Days
import com.example.proyecto.ui.theme.ProyectoTheme
import com.example.proyecto.models.Day


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DaysApp(
                        modifier = Modifier.padding(innerPadding).background(color = Color.Black),
                        days = Days.days
                    )
                }
            }
        }
    }
}

@Composable
fun DaysApp( modifier: Modifier = Modifier, days:List<Day>) {

    val lista = remember { mutableStateListOf<Day>().apply { addAll(days) } }

    ScrollInfinito(lista,
                   {lista.addAll(lista)},
                    modifier = modifier)
}

@Composable
fun ScrollInfinito(
    days: List<Day>,
    onCargarMas: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // true cuando el último elemento visible es uno de los últimos 3 de la lista
    val cercaDelFinal by remember {
        derivedStateOf {
            val ultimoVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            ultimoVisible >= listState.layoutInfo.totalItemsCount - 3
        }
    }

    LaunchedEffect(cercaDelFinal, days.size) {
        if (cercaDelFinal) onCargarMas()
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize().padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(days) { day -> DayCard(day)
        }
    }
}

@Composable
fun DayCard(day: Day, modifier: Modifier = Modifier){

    var expanded by rememberSaveable() { mutableStateOf(false) }

    Card(modifier = modifier
        .width(300.dp)
        .padding(5.dp)) {
        Column(modifier = Modifier
            .fillMaxSize().animateContentSize().background(MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.Start
        ){
            Text(text = stringResource(R.string.dia) + stringResource(day.numberRes),
                modifier = Modifier.padding(start = 5.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Image(painter = painterResource(day.imageRes), contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
                contentScale = ContentScale.FillBounds)

            DayItemButton(expanded = expanded, {expanded = !expanded})

            if (expanded) {
                Text(
                    text = stringResource(day.textRes),
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp, top = 2.dp),
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}


@Composable
private fun DayItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.btn_para_mostrar_curiosidad),
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DayCardPreview() {
    ProyectoTheme {
        DayCard(Day(R.drawable.image_19, R.string.Day1, R.string.One))
    }
}

@Preview(showBackground = true)
@Composable
    fun DaysAppPreview() {
    ProyectoTheme {
        DaysApp(modifier = Modifier, Days.days)
    }
}