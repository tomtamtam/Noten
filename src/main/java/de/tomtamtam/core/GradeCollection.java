package de.tomtamtam.core;

import java.util.ArrayList;
import java.util.List;

public class GradeCollection {
    private float perc;
    private List<Grade> grades;
    private CollectionType type;

    public GradeCollection(float perc, CollectionType type)
    {
        this.perc = perc;
        this.type = type;
        this.grades = new ArrayList<>();
    }

    public void AddGrde(Grade grade)
    {
        grades.add(grade);
    }

    public Grade[] GetAllGrades()
    {
        Grade[] gradeBuffer = new Grade[grades.size()];
        for(int i = 0; i < gradeBuffer.length; i++)
        {
            gradeBuffer[i] = grades.get(i);
        }
        return gradeBuffer;
    }

    public float GetGrade()
    {
        float entire = 0.0f;
        for(int i = 0; i < grades.size(); i++)
            entire += grades.get(i).GetValue();
        return entire / perc;
    }

    public CollectionType GetType()
    {
        return type;
    }
}
