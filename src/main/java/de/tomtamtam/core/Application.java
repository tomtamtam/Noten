package de.tomtamtam.core;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private float avg;
    private List<Subject> subjects;

    private static Application instance;

    private static Application get()
    {
        if(instance == null)
        {
            instance = new Application();
        }

        return instance;
    }

    private Application()
    {
        this.avg = 0.0f;
        this.subjects = new ArrayList<>();
    }

    private void addSubject(Subject subject)
    {
        subjects.add(subject);
    }

    private Subject getSubject(String name)
    {
        for(Subject s : subjects)
        {
            if(s.GetName() == name)
                return s;
        }
        return null;
    }

    private void removeSubject(String name)
    {
        for(int i = 0; i < subjects.size(); i++)
        {
            if(subjects.get(i).GetName() == name)
            {
                subjects.remove(i);
            }
        }
    }

    public static void AddSubject(String name)
    {
        get().addSubject(new Subject(name));
    }

    public static void RemoveSubject(String name)
    {
        get().removeSubject(name);
    }

    public static Subject GetSubject(String name)
    {
        return get().getSubject(name);
    }
}