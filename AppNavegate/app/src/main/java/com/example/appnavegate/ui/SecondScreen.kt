package com.example.appnavegate.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.appnavegate.R

@Composable
fun SecondScreen(
    counter: Int,
    onNextButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.second))
        Text(stringResource(R.string.counter, counter) + counter)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = onBackButtonClicked) { Text(stringResource(R.string.back)) }
            Button(onClick = onNextButtonClicked) { Text(stringResource(R.string.next)) }
        }
    }
}