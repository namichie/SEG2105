package com.qwerty123.cookhelper.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.qwerty123.cookhelper.Controller.Searching.SearchController;
import com.qwerty123.cookhelper.Model.RecipeBook.Recipe;
import com.qwerty123.cookhelper.R;
import com.qwerty123.cookhelper.View.RecipeListAdapter;

import java.util.ArrayList;

/**
 * Implements the functionality to allow the user to view the results of a search.
 */
public class SearchResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    ArrayList<Recipe> results;
    Recipe[] recipes;
    ListView searchResultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        results = SearchController.getLastResults();

        if(results != null && !results.isEmpty())
        {
            searchResultListView =(ListView)findViewById(R.id.SR_search_result_recipe_list);

            recipes = new Recipe[results.size()];
            results.toArray(recipes);

            searchResultListView.setAdapter(new RecipeListAdapter(this, R.layout.recipe_book_item, recipes));
            searchResultListView.setOnItemClickListener(this);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No valid search results", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        Intent intent = new Intent(view.getContext(), RecipeDetailViewActivity.class);
        intent.putExtra(getResources().getString(R.string.recipe_name_extra), recipes[position].getName());
        startActivity(intent);
    }
}
