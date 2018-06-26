package com.sandraprog.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sandraprog.bakingapp.R;
import com.sandraprog.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by sandrapog on 20.06.2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<Ingredient> mIngredientList;
    private Context mContext;


    public IngredientAdapter(List<Ingredient> mIngredientList, Context mContext) {
        this.mIngredientList = mIngredientList;
        this.mContext = mContext;
    }

    public void setIngredients(List<Ingredient> list) {
        this.mIngredientList = list;
    }

    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_content, parent, false);
        return new IngredientAdapter.IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.IngredientViewHolder holder, int position) {
        String ingrText = mIngredientList.get(position).getQuantity() + " " +
                mIngredientList.get(position).getMeasure() + " " +
                mIngredientList.get(position).getIngredient();

        holder.mIngredientName.setText(ingrText);

    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }


    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView mIngredientName;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            mIngredientName = itemView.findViewById(R.id.ingredient_name);
        }
    }
}
