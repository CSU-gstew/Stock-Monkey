package com.example.stockmonkey

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<LoginActivity>()

    @Test
    fun testInitialUiElementsAreDisplayed() {
        composeTestRule.onNodeWithText("Welcome to").assertIsDisplayed()
        composeTestRule.onNodeWithText("StockMonkey").assertIsDisplayed()
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Log In").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
    }

    @Test
    fun testEmptyInputsShowValidationErrors() {
        // Attempt to log in without entering credentials
        composeTestRule.onNodeWithText("Log In").performClick()

        // Validation messages from LoginSubmitButton logic
        composeTestRule.onNodeWithText("Username is required").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is required").assertIsDisplayed()
    }

    @Test
    fun testUsernameOnlyShowsPasswordError() {
        composeTestRule.onNodeWithText("Username").performTextInput("monkeyTrader")
        composeTestRule.onNodeWithText("Log In").performClick()

        composeTestRule.onNodeWithText("Password is required").assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Username is required").assertCountEquals(0)
    }

    @Test
    fun testPasswordOnlyShowsUsernameError() {
        composeTestRule.onNodeWithText("Password").performTextInput("Secret123!")
        composeTestRule.onNodeWithText("Log In").performClick()

        composeTestRule.onNodeWithText("Username is required").assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Password is required").assertCountEquals(0)
    }

    @Test
    fun testPasswordVisibilityToggle() {
        val toggleButton = composeTestRule.onNodeWithText("Show")
        toggleButton.assertIsDisplayed()
        toggleButton.performClick()
        composeTestRule.onNodeWithText("Hide").assertIsDisplayed()
    }
}