package com.android.artsytartsy;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.artsytartsy.ui.RecipeDetailsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by harminder on 17/03/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mActivityTestRule = new ActivityTestRule<>(RecipeDetailsActivity.class);

    @Test
    public void checkIfIngredientsCardIsDisplayed() {
        onView(withId(R.id.ingredients_card)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void checkIfStepsRecyclerViewIsEnabled() {
        onView(withId(R.id.steps_recyclerview)).check(ViewAssertions.matches(isEnabled()));
    }

}
