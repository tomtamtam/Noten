package de.tomtamtam.ui;

import javafx.scene.paint.Color;

public class SubjectPack 
{
    private String name;
    private float grade;

    public SubjectPack(String name, float grade)
    {
        this.name = name;
        this.grade = grade;
    }

    public Color GetGradeColor() 
    {
        float tempGrade = Math.max(1f, Math.min(6f, this.grade));
        float t = (tempGrade - 1f) / 5f;
        if (t <= 0.5f) 
        {
            float localT = t / 0.5f;
            return Color.color(localT, 1.0, 0.0);
        }
        else
        {
            float localT = (t - 0.5f) / 0.5f;
            return Color.color(1.0, 1.0 - localT, 0.0);
        }
    }

    public String GetName()
    {
        return name;
    }

    public float GetGrade()
    {
        return grade;
    }

}
