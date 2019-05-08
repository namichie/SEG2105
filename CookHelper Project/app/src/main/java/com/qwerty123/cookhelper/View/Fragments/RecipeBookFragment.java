package com.qwerty123.cookhelper.View.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.qwerty123.cookhelper.Model.RecipeBook.RecipeBook;
import com.qwerty123.cookhelper.View.Activities.RecipeDetailViewActivity;
import com.qwerty123.cookhelper.Controller.RecipeBookController;
import com.qwerty123.cookhelper.View.Activities.RecipeEditActivity;
import com.qwerty123.cookhelper.View.RecipeListAdapter;
import com.qwerty123.cookhelper.R;

/**
 * Fragment implementing the functionality to view the list of recipes, to navigate to the recipe's
 * detailed view, to navigate to the edit recipe activity and to delete recipes.
 */
public class RecipeBookFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    RecipeBook recipeBook;
    ListView recipeBookListView;
    RecipeListAdapter adapter;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        recipeBook = RecipeBookController.getRecipeBook();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        refreshListView();
    }

    private void refreshListView()
    {
        adapter = new RecipeListAdapter(recipeBookListView.getContext(), R.id.recipe_list_item_view, recipeBook.getRecipeArray());
        recipeBookListView.setAdapter(adapter);
        recipeBookListView.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View recipeBookView = inflater.inflate(R.layout.fragment_recipe_book, container, false);

        if (recipeBookView instanceof ListView)
        {
            recipeBookListView = (ListView) recipeBookView;
            recipeBookListView.setOnItemLongClickListener(this);

        }

        return recipeBookView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(view.getContext(), RecipeDetailViewActivity.class);
        intent.putExtra(getResources().getString(R.string.recipe_index_extra), position);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id)
    {
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(getActivity());
        dialogueBuilder.setMessage("Edit or delete recipe?");

        dialogueBuilder.setPositiveButton("delete", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                deleteRecipeAtPosition(position);
                Toast.makeText(getView().getContext(), "Recipe Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        dialogueBuilder.setNeutralButton("edit", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                editRecipeAtPosition(position);
            }
        });

        dialogueBuilder.create().show();

        return true;
    }

    private void deleteRecipeAtPosition(int position)
    {
        RecipeBookController.deleteRecipe(position);
        refreshListView();
    }

    private void editRecipeAtPosition(int position)
    {
        Context context = getView().getContext();
        Intent intent = new Intent(context, RecipeEditActivity.class);
        intent.putExtra(context.getResources().getString(R.string.recipe_index_extra), position);
        startActivity(intent);
    }
}
