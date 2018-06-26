package com.sandraprog.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sandraprog.bakingapp.MainActivity;
import com.sandraprog.bakingapp.R;
import com.sandraprog.bakingapp.RecipeDetailFragment;
import com.sandraprog.bakingapp.RecipeListActivity;
import com.sandraprog.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by sandrapog on 19.06.2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> mRecipeList;
    private Context mContext;
    private boolean mTwoPane;
    private final MainActivity mParentActivity;

    public RecipeAdapter(List<Recipe> mRecipeList, Context mContext, boolean mTwoPane, MainActivity parent) {
        this.mRecipeList = mRecipeList;
        this.mContext = mContext;
        this.mTwoPane = mTwoPane;
        this.mParentActivity = parent;
    }

    public void setRecipes(List<Recipe> list) {
        this.mRecipeList = list;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_content, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.mRecipeName.setText(mRecipeList.get(position).getName());
        holder.mRecipeServings.setText("Servings: " + mRecipeList.get(position).getServings().toString());
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private TextView mRecipeName;
        private TextView mRecipeServings;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mRecipeName = itemView.findViewById(R.id.recipe_name);
            mRecipeServings = itemView.findViewById(R.id.recipe_servings);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        final Recipe recipe = mRecipeList.get(position);
                        Context context = view.getContext();
                        Intent intent = new Intent(context, RecipeListActivity.class);
                        intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, recipe);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
