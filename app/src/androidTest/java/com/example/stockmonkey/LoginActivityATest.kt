//Disclaimer: AI was used in the editing and partial creation of the test cases to save time.

package com.example.stockmonkey

import android.content.Context
import android.view.View
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.runBlocking
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityATest {

    private lateinit var db: UserDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            .setDriver(BundledSQLiteDriver())
            .build()
        dao = db.userDao()

        // Insert mock user
        dao.insertAll(
            UserItem(
                id = 1,
                username = "monkeyTrader",
                password = "SecretPassword123",
                tickerList = emptyList()
            )
        )
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testInitialUiElementsAreDisplayed() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.tvLoginTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvLoginTitle)).check(matches(withText("Welcome!")))
        onView(withId(R.id.etLoginUsername)).check(matches(isDisplayed()))
        onView(withId(R.id.etLoginPassword)).check(matches(isDisplayed()))
        onView(withId(R.id.btnLoginSubmit)).check(matches(isDisplayed()))
        onView(withId(R.id.btnGoToSignUp)).check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyInputsShowValidationErrors() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.etLoginUsername)).perform(clearText(), closeSoftKeyboard())
        onView(withId(R.id.etLoginPassword)).perform(clearText(), closeSoftKeyboard())

        onView(withId(R.id.btnLoginSubmit)).perform(click())

        onView(withId(R.id.tilLoginUsername)).check(matches(hasTextInputLayoutErrorText("Username is required")))
        onView(withId(R.id.tilLoginPassword)).check(matches(hasTextInputLayoutErrorText("Password is required")))
    }

    @Test
    fun testUsernameOnlyShowsPasswordError() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.etLoginUsername)).perform(typeText("monkeyTrader"), closeSoftKeyboard())
        onView(withId(R.id.etLoginPassword)).perform(clearText(), closeSoftKeyboard())

        onView(withId(R.id.btnLoginSubmit)).perform(click())

        onView(withId(R.id.tilLoginPassword)).check(matches(hasTextInputLayoutErrorText("Password is required")))
    }

    @Test
    fun testCreateAccountButtonLaunchesWithoutCrash() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.btnGoToSignUp)).perform(click())
    }

    private fun hasTextInputLayoutErrorText(expectedError: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("has error text: $expectedError")
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is TextInputLayout) return false
                val error = view.error ?: return false
                return error.toString() == expectedError
            }
        }
    }
}