package de.tomtamtam;

import de.tomtamtam.core.*;
import de.tomtamtam.ui.GradeUI;

public class Main {
    public static void main(String[] args)
    {
        GradeApplication.AddSubject("Mathe");
        System.out.println(GradeApplication.GetSubject("Mathe").GetName());

        // Mathe Class Tests
        GradeApplication.GetSubject("Mathe").AddCollection(new GradeCollection(0.5f, CollectionType.ClassTest));
        GradeApplication.GetSubject("Mathe").GetCollection(CollectionType.ClassTest).AddGrade(new Grade(2.0f, "Arbeit 1"));
        GradeApplication.GetSubject("Mathe").GetCollection(CollectionType.ClassTest).AddGrade(new Grade(4.5f, "Arbeit 2"));

        //Mathe Oral Grade
        GradeApplication.GetSubject("Mathe").AddCollection(new GradeCollection(0.5f, CollectionType.OralGrade));
        GradeApplication.GetSubject("Mathe").GetCollection(CollectionType.OralGrade).AddGrade(new Grade(1.0f, "hj1"));

        System.out.println("Mathe Class Test avg: " + GradeApplication.GetSubject("Mathe").GetCollection(CollectionType.ClassTest).GetGrade());
        System.out.println("Mathe Oral avg: " + GradeApplication.GetSubject("Mathe").GetCollection(CollectionType.OralGrade).GetGrade());
        System.out.println("Mathe avg: " + GradeApplication.GetSubject("Mathe").GetGrade());

        //UI
        GradeUI.launch(args);

    }
}
