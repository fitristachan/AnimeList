package com.dicoding.animelist.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDatePicker(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
){
    val state = rememberDateRangePickerState(initialDisplayMode = DisplayMode.Input)

    val selectedStartDate = state.selectedStartDateMillis?.let {
        convertMillisToDate(it)
    }

    val selectedEndDate = state.selectedEndDateMillis?.let {
        convertMillisToDate(it)
    }?: "Not end yet"

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onDismiss() }) {
                Icon(Icons.Filled.Close, contentDescription = "Localized description")
            }
            TextButton(
                onClick = {
                    onDateSelected("$selectedStartDate - $selectedEndDate")
                    onDismiss()
                },
                enabled = state.selectedStartDateMillis != null
            ) {
                Text(text = "Save")
            }
        }
            DateRangePicker(state = state, modifier = Modifier.weight(1f))
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
    return formatter.format(Date(millis))
}