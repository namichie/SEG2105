package com.qwerty123.cookhelper.Utils.JSONSerialization;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


/**
 * A class which reads and writes JSON files from Android's local storage.
 */
public class JSONSerializer
{
    public static void writeObject(Context context, JSONSerializable jsonSerializable, String filename)
    {
        JSONObject jsonObject = jsonSerializable.toJSON();

        write(context, jsonObject, filename);
    }

    public static void writeArray(Context context, JSONSerializable[] jsonSerializables, String filename)
    {
        JSONArray jsonArray = new JSONArray();

        for (JSONSerializable jsonSerializable : jsonSerializables)
        {
            jsonArray.put(jsonSerializable.toJSON());
        }

        write(context, jsonArray, filename);
    }

    public static void writeArray(Context context, JSONArray jsonArray, String filename)
    {
        write(context, jsonArray, filename);
    }

    public static void writeList(Context context, List<JSONSerializable> jsonSerializables, String filename)
    {
        JSONArray jsonArray = new JSONArray();

        for (JSONSerializable jsonSerializable : jsonSerializables)
        {
            jsonArray.put(jsonSerializable.toJSON());
        }

        write(context, jsonArray, filename);
    }

    public static JSONObject readObject(Context context, String fileName)
    {
        String jsonString = readFileAsJSONString(context, fileName);
        JSONObject jsonObject = null;

        if (jsonString != null && !jsonString.isEmpty())
        {
            try
            {
                jsonObject = new JSONObject(jsonString);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }

    public static JSONArray readArray(Context context, String fileName)
    {
        String jsonString = readFileAsJSONString(context, fileName);
        JSONArray jsonArray = null;

        if (jsonString != null && !jsonString.isEmpty())
        {
            try
            {
                jsonArray = new JSONArray(jsonString);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    public static List<JSONObject> readList(Context context, String filename)
    {
        return null;
    }

    private static void write(Context context, JSONObject jsonObject, String filename)
    {
        if (jsonObject != null)
        {
            FileOutputStream outputStream;
            try
            {
                outputStream = context.openFileOutput(filename,
                        Context.MODE_PRIVATE);

                outputStream.write(jsonObject.toString(4).getBytes());
                outputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static void write(Context context, JSONArray jsonArray, String filename)
    {
        if (jsonArray != null)
        {
            FileOutputStream outputStream;
            try
            {
                outputStream = context.openFileOutput(filename,
                        Context.MODE_PRIVATE);

                outputStream.write(jsonArray.toString(4).getBytes());
                outputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static String readFileAsJSONString(Context context, String filename)
    {
        String jsonString = null;

        try
        {
            FileInputStream inputStream = context.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
                builder.append('\n');
            }

            jsonString = builder.toString();
            inputStream.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return jsonString;
    }
}

