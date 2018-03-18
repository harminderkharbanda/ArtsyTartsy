package com.android.artsytartsy;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.artsytartsy.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by harminder on 17/03/18.
 */

@RunWith(AndroidJUnit4.class)
public class WorkFlowTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;
    private String RECIPE_INTRO = "Recipe Introduction";

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickOnRecipe_CheckIfRecipeDetailScreenOpens() {
        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.ingredients_card)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void clickOnRecipe_ClickOnIngredientsCard_CheckIngredientsTextView() {
        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.ingredients_card)).perform(click());
        onView(withId(R.id.ingredients_details_tv)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void clickOnRecipe_ClickOnStep_CheckExoPlayerProgressBar() {
        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.steps_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.exo_pb)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void clickOnRecipe_ClickOnStep_CheckStepDescription() {
        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.steps_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText(RECIPE_INTRO)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
