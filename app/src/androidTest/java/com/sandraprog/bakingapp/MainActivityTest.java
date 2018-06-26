package com.sandraprog.bakingapp;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
 * Created by sandrapog on 24.06.2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    public static final String RECIPE_NAME = "Nutella Pie";
    public static final int RECIPE_POSITION = 0;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mMainActivity;

    @Before
    public void setActivity() {
        mMainActivity = mActivityTestRule.getActivity();
        SystemClock.sleep(3000);
    }

    @Test
    public void testRecipeNameAtPosition() {
        onView(withId(R.id.recycler_main))
                .perform(RecyclerViewActions.scrollToPosition(RECIPE_POSITION));
        onView(withText(RECIPE_NAME))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickRecipeAtPosition() throws Exception {
        onView(withId(R.id.recycler_main))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(RECIPE_POSITION, click()));
        onView(withId(R.id.ingredient_list))
                .check(matches(isDisplayed()));
    }

}
