package com.qwerty123.cookhelper.View.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qwerty123.cookhelper.Model.RecipeBook.PreparationStep;
import com.qwerty123.cookhelper.Model.RecipeBook.Recipe;
import com.qwerty123.cookhelper.Controller.RecipeBookController;
import com.qwerty123.cookhelper.R;
import com.qwerty123.cookhelper.Utils.Utils;

import java.util.ArrayList;

/**
 * Implements the functionality to allow the user to edit a recipe.
 */
public class RecipeEditActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final int STARTING_STEPS = 4;

    Recipe recipe;
    int recipeIndex;

    LinearLayout stepsLayout;

    EditText name;
    EditText category;
    EditText type;
    EditText prepTime;

    PreparationStep[] steps;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        getUIReferences();

        ((Button)findViewById(R.id.ER_add_step_button)).setOnClickListener(this);

        recipe = getRecipeReference();

        updateDisplay();
    }

    private void getUIReferences()
    {
        name = (EditText) findViewById(R.id.ER_recipe_name_field);
        category = (EditText) findViewById(R.id.ER_cultural_category_field);
        type = (EditText) findViewById(R.id.ER_meal_type_field);
        prepTime = (EditText) findViewById(R.id.ER_preparation_time_field);

        stepsLayout = (LinearLayout) findViewById(R.id.ER_steps_layout);
    }

    private Recipe getRecipeReference()
    {
        recipeIndex = getIntent().getIntExtra(getResources().getString(R.string.recipe_index_extra), -1);

        if (recipeIndex > -1)
        {
            return RecipeBookController.getRecipe(recipeIndex);
        }
        else
        {
            return null;
        }
    }

    private void updateDisplay()
    {
        if (recipe != null)
        {
            name.setText(recipe.getName());
            category.setText(recipe.getCulturalCategory().toString());
            type.setText(recipe.getMealType().toString());
            prepTime.setText(Integer.toString(recipe.getPreparationTime()));

            PreparationStep[] steps = recipe.getPreparationSteps();

            for (int i = 0; i < recipe.getNumberSteps(); ++i)
            {
                inflateEditRow(steps[i].getSpecificationString());
            }
        }
        else
        {
            for (int i = 0; i < STARTING_STEPS; ++i)
            {
                inflateEditRow(null);
            }
        }
    }

    public void addStep()
    {
        inflateEditRow(null);
    }

    public void deleteStep(View view)
    {
        stepsLayout.removeView(view);
    }

    private void inflateEditRow(String name)
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.step_edit_item, null);

        Button deleteButton = (Button) rowView.findViewById(R.id.SEI_delete_step_button);
        EditText instruction = (EditText) rowView.findViewById(R.id.SEI_instructions_field);

        if (name != null && !name.isEmpty())
        {
            instruction.setText(name);
        }

        deleteButton.setOnClickListener(this);

        stepsLayout.addView(rowView, stepsLayout.getChildCount());
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.ER_add_step_button)
        {
            addStep();
        }
        else if(view.getId() == R.id.SEI_delete_step_button)
        {
            View editStepItem = (View)view.getParent();
            deleteStep(editStepItem);
        }
    }

    public void onSaveButton(View view)
    {
        String recipeName = name.getText().toString();
        String recipeCategory = category.getText().toString();
        String recipeType = type.getText().toString();

        int recipePrepTime = obtainPrepTime();

        String[] recipeSteps = obtainSteps();

        if(recipe != null)
        {
            Toast.makeText(getApplicationContext(), recipe.getName() + " overridden", Toast.LENGTH_SHORT).show();
            RecipeBookController.overwriteRecipe(recipeIndex, recipeName, recipeCategory,recipeType, recipePrepTime, recipeSteps);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "New recipe saved", Toast.LENGTH_SHORT).show();
            RecipeBookController.addNewRecipe(recipeName, recipeCategory, recipeType, recipePrepTime, recipeSteps);
        }

        finish();
    }

    private int obtainPrepTime()
    {
        String prepTimeString = prepTime.getText().toString();

        if(!prepTimeString.isEmpty() && Utils.isNum(prepTimeString))
        {
            return Integer.parseInt(prepTimeString);
        }

        return 0;
    }

    private String[] obtainSteps()
    {
        ArrayList<String> steps = new ArrayList<>(stepsLayout.getChildCount());

        for(int i = 0; i < stepsLayout.getChildCount(); ++i)
        {
            View editStepItem = stepsLayout.getChildAt(i);

            EditText instructionField = (EditText)editStepItem .findViewById(R.id.SEI_instructions_field);

            String stepInfo = instructionField.getText().toString();

            if(!stepInfo.isEmpty())
            {
                steps.add(i, stepInfo);
            }
        }

        String[] recipeSteps = new String[steps.size()];
        steps.toArray(recipeSteps);

        return recipeSteps;
    }
}

