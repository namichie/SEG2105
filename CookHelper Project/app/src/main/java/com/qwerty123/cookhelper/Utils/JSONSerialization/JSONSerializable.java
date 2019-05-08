package com.qwerty123.cookhelper.Utils.JSONSerialization;

import org.json.JSONObject;

/**
 * A class which guaranties the property of being serializable with the JSON.org library.
 */
public interface JSONSerializable
{
    /**
     * @return converts the object to a representative JSON object.
     */
    public JSONObject toJSON();

    /**
     * Initializes members with the relevant information stored in the JSON object.
     * @param jsonObject
     */
    public void initializeFromJSON(JSONObject jsonObject);
}
