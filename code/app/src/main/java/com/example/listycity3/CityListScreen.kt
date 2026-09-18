package com.example.listycity3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

// The following screen changes were developed with assistance from OpenAI, Codex,
// "Keep the interface simple and comparable to the lab screenshots: a two-column city/province
// list, dividers, a top-right add button, and fields for adding or editing entries. Validate
// required fields and update the README and license with my student details.", 2026-09-18
// Prompt wording is an AI-assisted reconstruction of the original request.
@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onUpdateCity: (Int, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var cityName by remember { mutableStateOf("") }
    var provinceName by remember { mutableStateOf("") }
    var showCityFields by remember { mutableStateOf(false) }
    var selectedCityIndex by remember { mutableStateOf<Int?>(null) }
    val focusManager = LocalFocusManager.current
    val addCityLabel = stringResource(R.string.add_city)

    fun closeEditor() {
        showCityFields = false
        selectedCityIndex = null
        cityName = ""
        provinceName = ""
        focusManager.clearFocus()
    }

    Column(modifier = modifier.fillMaxSize().imePadding()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .semantics { contentDescription = addCityLabel },
                onClick = {
                    val wasAdding = showCityFields && selectedCityIndex == null
                    closeEditor()
                    showCityFields = !wasAdding
                }
            ) {
                Text("+", fontSize = 24.sp)
            }
        }

        if (showCityFields) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = cityName,
                        onValueChange = { cityName = it },
                        label = { Text(stringResource(R.string.city)) },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = provinceName,
                        onValueChange = { provinceName = it },
                        label = { Text(stringResource(R.string.province)) },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                ) {
                    TextButton(onClick = { closeEditor() }) {
                        Text(stringResource(R.string.cancel))
                    }
                    Button(
                        enabled = cityName.isNotBlank() && provinceName.isNotBlank(),
                        onClick = {
                            val city = City(cityName.trim(), provinceName.trim())
                            val index = selectedCityIndex
                            if (index == null) {
                                onAddCity(city)
                            } else {
                                onUpdateCity(index, city)
                            }
                            closeEditor()
                        }
                    ) {
                        Text(
                            stringResource(
                                if (selectedCityIndex == null) R.string.add_city else R.string.update_city
                            )
                        )
                    }
                }
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(cities) { index, city ->
                CityRow(
                    city = city,
                    onClick = {
                        selectedCityIndex = index
                        cityName = city.name
                        provinceName = city.province
                        showCityFields = true
                        focusManager.clearFocus()
                    }
                )
                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(city: City, onClick: () -> Unit) {
    val editLabel = stringResource(R.string.edit_city, city.name)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = editLabel, onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Toronto", "ON")
            ),
            onAddCity = {},
            onUpdateCity = { _, _ -> }
        )
    }
}
