package com.emma.bakingapp.UI;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.emma.bakingapp.R;
import com.emma.bakingapp.Ui.MainActivity;
import com.emma.bakingapp.Ui.RecipeDetailActivity;
import com.emma.bakingapp.Ui.VideoTutorialActivity;
import com.emma.bakingapp.Ui.WelcomeActivity;

import org.hamcrest.Matcher;
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
    public ActivityTestRule<WelcomeActivity> welcomeActivityActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    //this test shows that welcome activity is displayed
    @Test
    public void checkIfTextMatches(){
        onView(withId(R.id.baking_time)).check(matches(withText("Baking Time")));
    }

    @Test
    public void clickToEnterMainActivity(){

        //finds the start button witin the welcome activity
        Espresso.onView(withId(R.id.start_btn)).perform(ViewActions.click());

        //Opens up the mainActivity
        Intent intent = new Intent(welcomeActivityActivityTestRule.getActivity(), MainActivity.class);
        welcomeActivityActivityTestRule.launchActivity(intent);

    }



}
