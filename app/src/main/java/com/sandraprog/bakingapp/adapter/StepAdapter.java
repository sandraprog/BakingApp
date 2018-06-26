package com.sandraprog.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sandraprog.bakingapp.R;
import com.sandraprog.bakingapp.RecipeDetailActivity;
import com.sandraprog.bakingapp.RecipeDetailFragment;
import com.sandraprog.bakingapp.RecipeListActivity;
import com.sandraprog.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandrapog on 20.06.2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private List<Step> mStepList;
    private Context mContext;
    private boolean mTwoPane;
    private final RecipeListActivity mParentActivity;

    public StepAdapter(List<Step> mStepList, Context mContext, boolean mTwoPane, RecipeListActivity parent) {
        this.mStepList = mStepList;
        this.mContext = mContext;
        this.mTwoPane = mTwoPane;
        this.mParentActivity = parent;
    }

    public void setSteps(List<Step> list) {
        this.mStepList = list;
    }

    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_content, parent, false);
        return new StepAdapter.StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepViewHolder holder, int position) {
        holder.mStepName.setText(mStepList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }


    public class StepViewHolder extends RecyclerView.ViewHolder {
        private TextView mStepName;
        private TextView mStepServings;

        public StepViewHolder(View itemView) {
            super(itemView);
            mStepName = itemView.findViewById(R.id.step_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        final Step Step = mStepList.get(position);

                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putParcelableArrayList(RecipeDetailFragment.ARG_ITEM_ID, (ArrayList<? extends Parcelable>) mStepList);
                            arguments.putInt("id", position);
                            RecipeDetailFragment fragment = new RecipeDetailFragment();
                            fragment.setArguments(arguments);

                            mParentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.recipe_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, RecipeDetailActivity.class);
                            intent.putParcelableArrayListExtra(RecipeDetailFragment.ARG_ITEM_ID, (ArrayList<? extends Parcelable>) mStepList);
                            intent.putExtra("id", position);
                            Bundle arguments = new Bundle();
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }
}

