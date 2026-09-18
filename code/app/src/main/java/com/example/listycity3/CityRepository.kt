package com.example.listycity3

import androidx.compose.runtime.mutableStateListOf

// The following add/edit implementation was developed with assistance from OpenAI, Codex,
// "Implement the Lab 3 add-and-edit functionality in the existing Jetpack Compose ListyCity app.
// Use observable list state, preserve the immutable City model, and replace the selected entry
// when saving edits.", 2026-09-18
// Prompt wording is an AI-assisted reconstruction of the original request.
class CityRepository {
    private val _cities = mutableStateListOf(
        City("Edmonton", "AB"),
        City("Vancouver", "BC"),
        City("Toronto", "ON")
    )

    val cities: List<City>
        get() = _cities

    fun addCity(city: City) {
        _cities.add(city)
    }

    // Use the row index so identical cities can still be edited separately.
    fun updateCity(index: Int, updatedCity: City) {
        if (index in _cities.indices) {
            _cities[index] = updatedCity
        }
    }
}
