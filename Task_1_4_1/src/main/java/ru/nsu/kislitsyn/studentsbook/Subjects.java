package ru.nsu.kislitsyn.studentsbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subjects {
    private final List<Map<String, Mark>> subjects;
    private boolean retake;
    public Subjects(int numberOfSemesters) {
        this.retake = false;
        this.subjects = new ArrayList<>();
        for (int i = 0; i < numberOfSemesters; i++) {
            this.subjects.add(new HashMap<>());
        }
    }

    public boolean getRetake() {
        return this.retake;
    }

    public List<Map<String, Mark>> getList() {
        return subjects;
    }

    public void add(int semester, String subject, Mark mark) {
        if (this.getSemester(semester).containsKey(subject)) {
            retake = true;
        }
        this.subjects.get(semester - 1).put(subject, mark);
    }

    public Map<String, Mark> getSemester(int semester) {
        return subjects.get(semester - 1);
    }

    public Mark getMark(int semester, String subject) {
        if (!this.getSemester(semester).containsKey(subject)) {
            System.out.println("There is no such subject");
            return null;
        }
        return this.getSemester(semester).get(subject);
    }
}
