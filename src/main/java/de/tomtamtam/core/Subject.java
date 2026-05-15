package de.tomtamtam.core;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<GradeCollection> collections;
    private String name;

    public Subject(String name)
    {
        this.name = name;
        this.collections = new ArrayList<>();
    }

    public String GetName()
    {
        return name;
    }

    public void AddCollection(GradeCollection collection)
    {
        collections.add(collection);
    }

    public void RemoveCollection(CollectionType type)
    {
        collections.removeIf(c -> c.GetType() == type);
    }

    public GradeCollection GetCollection(CollectionType type)
    {
        for (GradeCollection c : collections)
        {
            if(c.GetType() == type)
                return c;
        }
        return null;
    }

    public float GetGrade()
    {
        float entire = 0.0f;
        for(GradeCollection c : collections)
            entire += c.GetGrade();
        return  entire;
    }
}
