package com.example.listycity3

import org.junit.Assert.assertEquals
import org.junit.Test

class CityRepositoryTest {
    @Test
    fun updatingDuplicateChangesOnlySelectedRow() {
        val repository = CityRepository()
        val original = repository.cities.first()
        repository.addCity(original.copy())

        val updated = City("St. Albert", "AB")
        repository.updateCity(repository.cities.lastIndex, updated)

        assertEquals(4, repository.cities.size)
        assertEquals(original, repository.cities.first())
        assertEquals(updated, repository.cities.last())
    }

    @Test
    fun editingReplacesBothFieldsAndPreservesOrder() {
        val repository = CityRepository()
        repository.updateCity(1, City("Montreal", "QC"))

        assertEquals(
            listOf(City("Edmonton", "AB"), City("Montreal", "QC"), City("Toronto", "ON")),
            repository.cities
        )
    }
}
