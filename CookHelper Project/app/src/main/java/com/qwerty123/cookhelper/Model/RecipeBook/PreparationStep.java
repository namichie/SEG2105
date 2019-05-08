package com.qwerty123.cookhelper.Model.RecipeBook;

/**
 * Represents a step in a recipe. Also has a reference to the ingredients used.
 */
public class PreparationStep
{
    //String that contains the #ingredientName type of tokens.
    private String specificationString;

    //String intended for viewing. Removed the # in the specification string.
    private String displayString;

    private Ingredient[] ingredients;

    /**
     * @param specificationString a string in which all ingredients are written without whitespaces
     *                            within them and are prefixed with a '#' symbol. This is parsed to
     *                            create the appropriate ingredient objects.
     * @param displayString a string for display purposes, provided separately.
     * @param ingredients the list of ingredients involved.
     */
    public PreparationStep(String specificationString, String displayString, Ingredient[] ingredients)
    {
        this.specificationString = specificationString;
        this.displayString = displayString;
        this.ingredients = ingredients;
    }

    /**
     * @return the display string
     */
    public String getDisplayString()
    {
        return displayString;
    }

    /**
     * @return the string with the symbols.
     */
    public String getSpecificationString()
    {
        return specificationString;
    }

    public Ingredient[] getIngredients()
    {
        return ingredients;
    }
}
