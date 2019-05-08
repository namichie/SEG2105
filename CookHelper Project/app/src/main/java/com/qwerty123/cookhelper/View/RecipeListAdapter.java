package com.qwerty123.cookhelper.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qwerty123.cookhelper.Model.RecipeBook.Recipe;
import com.qwerty123.cookhelper.R;

/**
 * An adapter to inflate list items representing a recipe.
 */
public class RecipeListAdapter extends ArrayAdapter<Recipe>
{
    public RecipeListAdapter(Context context, int resource, Recipe[] objects)
    {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.recipe_book_item, parent, false);
        TextView nameTextView = (TextView) rowView.findViewById(R.id.recipe_name);
        TextView categoryTextView = (TextView) rowView.findViewById(R.id.recipe_category);
        TextView typeTextView = (TextView) rowView.findViewById(R.id.recipe_type);

        Recipe recipe = getItem(position);

        if (recipe != null)
        {
            nameTextView.setText(String.format("%1$s", recipe.getName()));
            categoryTextView.setText(String.format("Culture: %1$s", recipe.getCulturalCategory().getDisplayName()));
            typeTextView.setText(String.format("Meal: %1$s", recipe.getMealType().getDisplayName()));
        }
        else
        {
            nameTextView.setText(String.format("%1$s", "ERROR"));
            categoryTextView.setText(String.format("Culture: %1$s", "EMPTY"));
            typeTextView.setText(String.format("Meal: %1$s", "EMPTY"));
        }

        return rowView;
    }
}
