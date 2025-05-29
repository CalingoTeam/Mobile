package com.example.calingo;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;

@RunWith(AndroidJUnit4.class)
public class TelaLoginInstrumentedTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(TelaLogin.class);
    }

    @Test
    public void testCamposVisiveis() {
        onView(withId(R.id.input_email)).check(matches(isDisplayed()));
        onView(withId(R.id.input_password)).check(matches(isDisplayed()));
        onView(withId(R.id.entrar_bt)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginCamposVazios() {
        onView(withId(R.id.entrar_bt)).perform(click());
    }

    @Test
    public void testPreenchimentoCampos() {
        onView(withId(R.id.input_email)).perform(typeText("teste@email.com"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.entrar_bt)).perform(click());
    }
}
