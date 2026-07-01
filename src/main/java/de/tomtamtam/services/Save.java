package de.tomtamtam.services;

import org.json.simple.JSONObject;

public class Save {

    private Save() {}
    private Save instance;

    private Save get()
    {
        if(instance == null)
        {
            instance = new Save();
        }

        return instance;
    }

    private void save()
    {

    }
}
