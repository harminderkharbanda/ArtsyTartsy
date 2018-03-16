package com.android.artsytartsy;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.artsytartsy.data.data.model.Recipe;
import com.android.artsytartsy.data.data.model.Step;

import java.util.List;
import java.util.Random;

/**
 * Created by harminder on 14/03/18.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {

    private final RecipeStepClickListener listener;
    private List<Step> recipeStepList;

    public RecipeStepsAdapter(RecipeStepClickListener listener) {
        this.listener = listener;
    }

    public interface RecipeStepClickListener{
        void onRecipeStepClicked(Step step);
    }

    @Override
    public RecipeStepsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.steps_item, viewGroup, false);
        return new RecipeStepsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeStepsViewHolder holder, int position) {
        Step step = recipeStepList.get(position);
        holder.stepName.setText(step.getShortDescription());
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.stepCard.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        if (null == recipeStepList)return 0;
        return recipeStepList.size();
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView stepName;
        private CardView stepCard;
        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
            stepName = itemView.findViewById(R.id.step_name);
            stepCard = itemView.findViewById(R.id.steps_card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRecipeStepClicked(recipeStepList.get(getAdapterPosition()));
        }
    }

    public void setStepsData(List<Step> stepData) {
        recipeStepList = stepData;
    }

}
