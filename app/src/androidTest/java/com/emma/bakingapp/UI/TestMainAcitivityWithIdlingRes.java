package com.emma.bakingapp.UI;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.emma.bakingapp.R;
import com.emma.bakingapp.Ui.MainActivity;
import com.emma.bakingapp.Ui.RecipeDetailActivity;
import com.emma.bakingapp.Ui.VideoTutorialActivity;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
public class TestMainAcitivityWithIdlingRes {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void idlingTest() {

        //register the idling resource
        Espresso.registerIdlingResources(mainActivityActivityTestRule.getActivity().getIdlingResource());

        //click on the second item in the recycler view
        Espresso.onView(ViewMatchers.withId(R.id.recipes_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));

        //opens the recipe detail activity
        Intent intent = new Intent(mainActivityActivityTestRule.getActivity(), RecipeDetailActivity.class);
        mainActivityActivityTestRule.launchActivity(intent);

    }

}
