package de.tomtamtam.core;

import java.util.ArrayList;
import java.util.List;
import de.tomtamtam.core.CollectionType;

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
        for(int i = 0; i < collections.size(); i++)
        {
            if(collections.get(i).GetType() == type)
            {
                collections.remove(i);
            }
        }
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
}
