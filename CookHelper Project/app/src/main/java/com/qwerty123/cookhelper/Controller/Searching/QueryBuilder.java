package com.qwerty123.cookhelper.Controller.Searching;

import android.support.annotation.NonNull;
import com.qwerty123.cookhelper.Utils.Utils;

import java.util.Scanner;

/**
 * A specialized class to construct a query. Queries are a little complex, and
 * take two distinct steps to create, one to receive the name, category, type and prep time, and
 * the other to parse the list of required, optional and excluded ingredients.
 */
public class QueryBuilder
{
    String nameCriteria;
    String categoryCriteria;
    String typeCriteria;
    int prepTimeCriteria;
    IngredientQueryGroup ingredientQueryGroup;

    //Constants for parsing
    final char NOT_TOKEN = '-';
    final char AND_TOKEN = '&';
    final char OR_TOKEN = '+';

    public QueryBuilder(String name, String category, String type, int prepTime)
    {
        nameCriteria = name;
        categoryCriteria = category;
        typeCriteria = type;
        prepTimeCriteria = prepTime;
        ingredientQueryGroup = null;
    }

    /**
     * @return a query containing all the attributes passed to the constructor and the
     * parseIngredientsCriteria method.
     */
    public Query buildQuery()
    {
        return new Query(nameCriteria, categoryCriteria, typeCriteria, prepTimeCriteria, ingredientQueryGroup);
    }

    /**
     * @param ingredientCriteria a string representing the list of required, optional and excluded
     *                           ingredients.
     * @return a boolean representing the success or failure of the parsing process.
     */
    public boolean parseIngredientCriteria(String ingredientCriteria)
    {
        if(ingredientCriteria != null && !ingredientCriteria.isEmpty())
        {
            Scanner scanner = new Scanner(ingredientCriteria);

            IngredientQueryGroup ingredientQueryGroup = new IngredientQueryGroup();

            while (scanner.hasNext())
            {
                String token = scanner.next();
                char operator = 0;
                String ingredientName = null;

                if (!token.isEmpty())
                {
                    boolean parseOperator = checkParseOperator(token);

                    if (parseOperator)
                    {
                        operator = getOperator(token);

                        if (operator == 0)
                        {
                            return false;
                        }
                        else
                        {
                            token = removeOperatorFromToken(token);
                        }
                    }

                    boolean parseWord = checkParseIngredient(token);

                    if (parseWord)
                    {
                        if (token.length() > 1 && Utils.isAlphaOrUnderscore(token))
                        {
                            ingredientName = token;
                        }
                        else
                        {
                            return false;
                        }

                        if (operator == 0)
                        {
                            operator = AND_TOKEN;
                        }
                    }

                    if (operator != 0 && token.isEmpty())
                    {
                        if (scanner.hasNext())
                        {
                            String ingredient = scanner.next();
                            if (Utils.isAlphaOrUnderscore(ingredient))
                            {
                                ingredientName = ingredient;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }
                }

                if (operator != 0 && ingredientName != null && !ingredientName.isEmpty())
                {
                    switch (operator)
                    {
                        case NOT_TOKEN:
                            ingredientQueryGroup.addIngredientExclude(ingredientName);
                            break;
                        case AND_TOKEN:
                            ingredientQueryGroup.addIngredientRequired(ingredientName);
                            break;
                        case OR_TOKEN:
                            ingredientQueryGroup.addIngredientOptional(ingredientName);
                            break;
                    }
                }
            }

            this.ingredientQueryGroup = ingredientQueryGroup;
            return true;
        }
        else
        {
            return true;
        }
    }

    /**
     * @param token a string containing an operator (&, +, -)
     * @return a substring without the operator.
     */
    @NonNull
    private String removeOperatorFromToken(String token)
    {
        token = token.length() > 1 ? token.substring(1) : "";
        return token;
    }

    /**
     * @param token a string containing an operator
     * @return a character representing that operator
     */
    private char getOperator(String token)
    {
        char operator;
        switch (token.charAt(0))
        {
            case NOT_TOKEN:
                operator = NOT_TOKEN;
                break;
            case AND_TOKEN:
                operator = AND_TOKEN;
                break;
            case OR_TOKEN:
                operator = OR_TOKEN;
                break;
            default:
                operator = 0;
                break;
        }
        return operator;
    }

    /**
     * @param token
     * @return whether or not the string is long enough to be a word.
     */
    private boolean checkParseIngredient(String token)
    {
        return token.length() > 1;
    }

    /**
     * @param token
     * @return whether or not there is an operator in the token.
     */
    private boolean checkParseOperator(String token)
    {
        char c;

        if (token.length() >= 1)
        {
            c = token.charAt(0);

            return (c == NOT_TOKEN || c == AND_TOKEN || c == OR_TOKEN);
        }
        else
        {
            return false;
        }
    }
}
