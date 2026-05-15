package de.tomtamtam;

import de.tomtamtam.core.*;

public class Main {
    public static void main(String[] args)
    {
        Application.AddSubject("Mathe");
        System.out.println(Application.GetSubject("Mathe").GetName());
        Application.GetSubject("Mathe").AddCollection(new GradeCollection(1.0f, CollectionType.ClassTest));
        Application.GetSubject("Mathe").GetCollection(CollectionType.ClassTest).AddGrde(new Grade(2.0f, "Arbeit 1"));
        Application.GetSubject("Mathe").GetCollection(CollectionType.ClassTest).AddGrde(new Grade(4.5f, "Arbeit 2"));
        //System.out.println("Mathe Class Test avg: " + Application.GetSubject("Mathe").GetCollection(CollectionType.ClassTest).GetGrade());
    }
}