package com.sandraprog.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sandraprog.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    private List<Step> mStepList;
    private Step mStep;
    private int position;
    private RecipeDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setTitle("");
            }

            Bundle arguments = new Bundle();
            Intent intentThatStartedThisActivity = getIntent();
            if (intentThatStartedThisActivity.hasExtra(RecipeDetailFragment.ARG_ITEM_ID)) {

                mStepList = getIntent().getParcelableArrayListExtra(RecipeDetailFragment.ARG_ITEM_ID);
                position = getIntent().getIntExtra("id", 0);

                arguments.putParcelableArrayList(RecipeDetailFragment.ARG_ITEM_ID, (ArrayList<? extends Parcelable>) mStepList);
                arguments.putInt("id", position);

                fragment = new RecipeDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipe_detail_container, fragment)
                        .commit();
            } else {
                Toast.makeText(this, R.string.no_data_text, Toast.LENGTH_SHORT).show();
            }
        }

        Button nextButton = this.findViewById(R.id.button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < mStepList.size() - 1) {
                    position++;
                    fragment.setupView(position);
                }
            }
        });
        Button prevButton = this.findViewById(R.id.button_prev);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0) {
                    position--;
                    fragment.setupView(position);
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (this.getSupportActionBar() != null) {
                this.getSupportActionBar().hide();
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (this.getSupportActionBar() != null) {
                this.getSupportActionBar().show();
            }
        }
    }

}
