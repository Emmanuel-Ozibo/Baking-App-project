package com.emma.bakingapp.UI;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.emma.bakingapp.R;
import com.emma.bakingapp.Ui.WelcomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class BakingAppUiTest {
    @Rule
    public ActivityTestRule<WelcomeActivity> mainActivityActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    //this test shows that welcome activity is displayed
    @Test
    public void checkIfTextMatches(){
        onView(withId(R.id.baking_time)).check(matches(withText("Baking Time")));
    }

}
