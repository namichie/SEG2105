package com.qwerty123.cookhelper.View.Fragments;

import android.content.Intent;
import android.support.annotation.Nullable;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qwerty123.cookhelper.Controller.Searching.Query;
import com.qwerty123.cookhelper.Controller.Searching.QueryBuilder;
import com.qwerty123.cookhelper.Controller.Searching.SearchController;
import com.qwerty123.cookhelper.Model.RecipeBook.Recipe;
import com.qwerty123.cookhelper.R;
import com.qwerty123.cookhelper.Utils.Utils;
import com.qwerty123.cookhelper.View.Activities.SearchResultActivity;

import java.util.ArrayList;

/**
 * Fragment implementing the functionality for recipes to search by filtering the many attributes of a recipe.
 */
public class AdvancedSearchFragment extends Fragment implements View.OnClickListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_advanced_search, container, false);

        Button searchButton = (Button)view.findViewById(R.id.AS_search_button);
        searchButton.setOnClickListener(this);

        return view;
    }

    public void onClickSearchButton(View view)
    {
        EditText name = (EditText) getView().findViewById(R.id.AS_recipe_name_field);
        EditText category = (EditText) getView().findViewById(R.id.AS_category_label);
        EditText type = (EditText) getView().findViewById(R.id.AS_meal_type_label);
        EditText prepTime = (EditText) getView().findViewById(R.id.AS_preparation_time_field);
        EditText ingredients = (EditText) getView().findViewById(R.id.AS_ingredients);

        String nameCriteria = name.getText().toString();
        String categoryCriteria = category.getText().toString();
        String typeCriteria = type.getText().toString();

        int prepTimeCriteria = 0;
        String prepTimeString = prepTime.getText().toString();

        if(!prepTimeString.isEmpty() && Utils.isNum(prepTimeString))
        {
            prepTimeCriteria = Integer.parseInt(prepTimeString);
        }

        String ingredientsCriteria = ingredients.getText().toString();

        if (!nameCriteria.isEmpty() || !categoryCriteria.isEmpty() || !typeCriteria.isEmpty() || prepTimeCriteria > 0 || !ingredientsCriteria.isEmpty())
        {
            QueryBuilder queryBuilder = new QueryBuilder(nameCriteria, categoryCriteria, typeCriteria, prepTimeCriteria);
            Query query = null;

            if (!queryBuilder.parseIngredientCriteria(ingredientsCriteria))
            {
                promptUserForValidInput("The ingredient criteria is not valid.");
            }
            else
            {
                query = queryBuilder.buildQuery();
            }

            if (query != null)
            {
                ArrayList<Recipe> results = SearchController.performSearch(query);

                if(!results.isEmpty())
                {
                    Intent intent = new Intent(getView().getContext(), SearchResultActivity.class);
                    startActivity(intent);
                }
                else
                {
                    promptUserForValidInput("No recipes matched criteria.");
                }
            }
        }
        else
        {
            promptUserForValidInput("No valid criteria found.");
        }
    }

    private void promptUserForValidInput(String message)
    {
        Toast.makeText(getView().getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.AS_search_button)
        {
            onClickSearchButton(view);
        }
    }
}




