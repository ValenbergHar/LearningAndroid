package com.example.pizzarecipes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PizzaRecipeAdapter extends RecyclerView.Adapter<PizzaRecipeAdapter.PizzaRecipeViewHolder> {



    ArrayList<PizzaRecipeItem> pizzaRecipeItems;
    Context context;
    public PizzaRecipeAdapter(ArrayList<PizzaRecipeItem> pizzaRecipeItems, Context context){
        this.pizzaRecipeItems = pizzaRecipeItems;
        this. context=context;
    }

    @NonNull
    @Override
    public PizzaRecipeAdapter.PizzaRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_recipe, parent, false);
        PizzaRecipeViewHolder pizzaRecipeViewHolder = new PizzaRecipeViewHolder(view);

        return pizzaRecipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaRecipeViewHolder holder, int position) {
        PizzaRecipeItem pizzaRecipeItem = pizzaRecipeItems.get(position);
        holder.pizzaImageView.setImageResource(pizzaRecipeItem.getImageResource());
        holder.title.setText(pizzaRecipeItem.getTitle());
        holder.description.setText(pizzaRecipeItem.getDescription());
        holder.recipe.setText(pizzaRecipeItem.getRecipe());
    }


    @Override
    public int getItemCount() {
        return pizzaRecipeItems.size();
    }

    public  class PizzaRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ImageView pizzaImageView;
        public TextView title;
        public TextView description;
        public TextView recipe;

        PizzaRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            pizzaImageView = itemView.findViewById(R.id.pizzaImageView);
            title = itemView.findViewById(R.id.titleTextView);
            description = itemView.findViewById(R.id.descriptionTextView);
            recipe = itemView.findViewById(R.id.recipeTextView);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            PizzaRecipeItem pizzaRecipeItem = pizzaRecipeItems.get(position);

            Intent intent = new Intent(context, RecipeActivity.class);
            intent.putExtra("imageResource", pizzaRecipeItem.getImageResource());
            intent.putExtra("title", pizzaRecipeItem.getTitle());
            intent.putExtra("description", pizzaRecipeItem.getDescription());
            intent.putExtra("recipe",  pizzaRecipeItem.getRecipe());

            context.startActivity(intent);

        }
    }
}
