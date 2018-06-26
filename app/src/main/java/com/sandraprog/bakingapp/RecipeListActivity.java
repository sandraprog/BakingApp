package com.sandraprog.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.sandraprog.bakingapp.adapter.IngredientAdapter;
import com.sandraprog.bakingapp.adapter.StepAdapter;
import com.sandraprog.bakingapp.model.Ingredient;
import com.sandraprog.bakingapp.model.Recipe;
import com.sandraprog.bakingapp.model.Step;

import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private RecyclerView recyclerViewSteps;
    private RecyclerView recyclerViewIngredients;
    private List<Recipe> mList;
    private IngredientAdapter mIngredientAdapter;
    private StepAdapter mStepAdapter;
    private RecipeListActivity mInstance;

    private Recipe mRecipe;
    private List<Step> mStepList;
    private List<Ingredient> mIngredientList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mInstance = this;
        mContext = this;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(RecipeDetailFragment.ARG_ITEM_ID)) {
            mRecipe = getIntent().getParcelableExtra(RecipeDetailFragment.ARG_ITEM_ID);
            mStepList = mRecipe.getSteps();
            mIngredientList = mRecipe.getIngredients();
            saveIngredientsToSP();
            getSupportActionBar().setTitle(mRecipe.getName());
            initViews();
        } else {
            Toast.makeText(this, R.string.no_data_text, Toast.LENGTH_SHORT).show();
        }

    }

    private void saveIngredientsToSP() {
        String widgetText = "";
        for (int i = 0; i < mIngredientList.size(); i++) {
            widgetText = widgetText + mIngredientList.get(i).getQuantity();
            widgetText = widgetText + " " + mIngredientList.get(i).getMeasure();
            widgetText = widgetText + " " + mIngredientList.get(i).getIngredient();
            widgetText = widgetText + "\n";
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(getString(R.string.saved_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.key_string), widgetText);
        editor.putString(getString(R.string.recipe_key_string), mRecipe.getName());
        editor.commit();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget);
        ComponentName thisWidget = new ComponentName(mContext, RecipeWidget.class);

        remoteViews.setTextViewText(R.id.appwidget_ingredients_list, widgetText);
        remoteViews.setTextViewText(R.id.appwidget_text, mRecipe.getName());

        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

    }

    private void initViews() {

        recyclerViewIngredients = findViewById(R.id.ingredient_list);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewIngredients.setHasFixedSize(false);
        mIngredientAdapter = new IngredientAdapter(mIngredientList, this);
        recyclerViewIngredients.setAdapter(mIngredientAdapter);

        recyclerViewSteps = findViewById(R.id.step_list);
        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewIngredients.setHasFixedSize(false);
        mStepAdapter = new StepAdapter(mStepList, this, mTwoPane, mInstance);
        recyclerViewSteps.setAdapter(mStepAdapter);
    }


}
