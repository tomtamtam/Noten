package de.tomtamtam.core;

public class Grade {
    private float value;
    private String id;

    public Grade(float value, String id)
    {
        this.id = id;
        this.value = value;
    }

    public float GetValue()
    {
        return value;
    }

    public String GetID()
    {
        return id;
    }
}
