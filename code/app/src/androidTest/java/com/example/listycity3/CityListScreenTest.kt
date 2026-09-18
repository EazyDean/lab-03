package com.example.listycity3

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import org.junit.Rule
import org.junit.Test

class CityListScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addingCityTrimsInputAndClosesForm() {
        composeRule.onNodeWithContentDescription("Add City").performClick()
        composeRule.onNodeWithText("City").performTextReplacement(" Victoria ")
        composeRule.onNodeWithText("Province").performTextReplacement(" BC ")
        composeRule.onNodeWithText("Add City").performClick()

        composeRule.onNodeWithText("Victoria").assertIsDisplayed()
        composeRule.onNodeWithText("City").assertDoesNotExist()
        composeRule.onNodeWithText("Edmonton").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Add City").performClick()
        composeRule.onNodeWithText("Add City").assertIsNotEnabled()
    }

    @Test
    fun editingCityPrefillsAndUpdatesBothFields() {
        composeRule.onNodeWithText("Vancouver").performClick()
        composeRule.onNodeWithText("City").assertTextContains("Vancouver")
        composeRule.onNodeWithText("Province").assertTextContains("BC")

        composeRule.onNodeWithText("City").performTextReplacement("St. Albert")
        composeRule.onNodeWithText("Province").performTextReplacement("AB")
        composeRule.onNodeWithText("Update City").performClick()

        composeRule.onNodeWithText("St. Albert").assertIsDisplayed()
        composeRule.onNodeWithText("Vancouver").assertDoesNotExist()
        composeRule.onNodeWithText("City").assertDoesNotExist()
        composeRule.onNodeWithText("Toronto").assertIsDisplayed()
    }

    @Test
    fun cancellingEditKeepsOriginalCity() {
        composeRule.onNodeWithText("Vancouver").performClick()
        composeRule.onNodeWithText("City").performTextReplacement("Victoria")
        composeRule.onNodeWithText("Cancel").performClick()

        composeRule.onNodeWithText("Vancouver").assertIsDisplayed()
        composeRule.onNodeWithText("Victoria").assertDoesNotExist()
        composeRule.onNodeWithText("City").assertDoesNotExist()
    }

    @Test
    fun blankFieldsCannotBeAddedOrSaved() {
        composeRule.onNodeWithContentDescription("Add City").performClick()
        composeRule.onNodeWithText("Add City").assertIsNotEnabled()
        composeRule.onNodeWithText("City").performTextReplacement("Calgary")
        composeRule.onNodeWithText("Province").performTextReplacement("   ")
        composeRule.onNodeWithText("Add City").assertIsNotEnabled()
        composeRule.onNodeWithText("Cancel").performClick()

        composeRule.onNodeWithText("Edmonton").performClick()
        composeRule.onNodeWithText("City").performTextReplacement("   ")
        composeRule.onNodeWithText("Update City").assertIsNotEnabled()
    }
}
