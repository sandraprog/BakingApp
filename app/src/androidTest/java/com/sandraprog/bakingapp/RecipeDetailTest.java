package com.sandraprog.bakingapp;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by sandrapog on 26.06.2018.
 */

public class RecipeDetailTest {
    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityRecipeDetailTestRule = new ActivityTestRule<>(RecipeDetailActivity.class);

    @Test
    public void testRecipeDetailContainer_Visibility() {
        onView(withId(R.id.recipe_detail_container))
                .check(matches(isDisplayed()));

    }
}
