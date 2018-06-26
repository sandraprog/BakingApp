package com.sandraprog.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Toast;

import com.sandraprog.bakingapp.adapter.RecipeAdapter;
import com.sandraprog.bakingapp.api.RetrofitClient;
import com.sandraprog.bakingapp.api.RetrofitInterface;
import com.sandraprog.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private boolean mTwoPane;
    private RecyclerView recyclerView;
    private List<Recipe> mList;
    private RecipeAdapter mAdapter;
    private MainActivity mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInstance = this;

        mTwoPane = getResources().getBoolean(R.bool.isTablet);
        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        mList = new ArrayList<>();
        mAdapter = new RecipeAdapter(mList, this, mTwoPane, mInstance);

        if (mTwoPane)
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(mAdapter);

        loadJSON();
    }

    private void loadJSON() {
        RetrofitInterface apiService = RetrofitClient.getClient().create(RetrofitInterface.class);

        Call<List<Recipe>> call = apiService.getRecipeList();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    mList = response.body();
                    mAdapter = new RecipeAdapter(mList, getApplicationContext(), mTwoPane, mInstance);
                    recyclerView.setAdapter(mAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error_network, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
