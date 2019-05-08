package com.qwerty123.cookhelper.Utils;

import android.content.Context;

/**
 * A collection of utility methods.
 */
public class Utils
{
    private static Context applicationContext = null;

    public static void setApplicationContext(Context context)
    {
            applicationContext = context;
    }

    public static Context getApplicationContext()
    {
        return applicationContext;
    }

    /**
     * Checks if a string is both alphabetical and only contains underscore as special characters.
     * @param string
     * @return
     */
    public static boolean isAlphaOrUnderscore(String string)
    {
        return string.matches("[a-zA-Z_]+");
    }

    /**
     * Checks if a string is numerical
     * @param string
     * @return
     */
    public static boolean isNum(String string)
    {
        return string.matches("[0-9]+");
    }

    /**
     * Creates a display name used by the category, type and ingredients.
     * @param name
     * @return
     */
    public static String createDisplayName(String name)
    {
       StringBuilder builder = new StringBuilder();

            for(int i = 0; i < name.length(); ++i)
            {
                if(name.charAt(i) == '_')
                {
                    builder.append(' ');
                }
                else
                {
                    builder.append(name.charAt(i));
                }
            }

            return builder.toString();
    }
}
