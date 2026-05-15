package de.tomtamtam.core;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GradeApplication extends Application {
    private float avg;
    private List<Subject> subjects;

    private static GradeApplication instance;

    private static GradeApplication get()
    {
        if (instance == null) {
            instance = new GradeApplication();
        }

        return instance;
    }

    private GradeApplication() {
        this.avg = 0.0f;
        this.subjects = new ArrayList<>();
    }

    private void run(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {

    }

    private void addSubject(Subject subject) {
        subjects.add(subject);
    }

    private Subject getSubject(String name) {
        for (Subject s : subjects) {
            if (s.GetName() == name)
                return s;
        }
        return null;
    }

    private void removeSubject(String name) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).GetName() == name) {
                subjects.remove(i);
            }
        }
    }

    public static void AddSubject(String name) {
        get().addSubject(new Subject(name));
    }

    public static void RemoveSubject(String name) {
        get().removeSubject(name);
    }

    public static Subject GetSubject(String name)
    {
        return get().getSubject(name);
    }

    public static void Run(String[] args)
    {
        get().run(args);
    }
}