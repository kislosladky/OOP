package ru.nsu.kislitsyn.studentsbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Class to store marks.
*/
public class Subjects {
    private final List<Map<String, Mark>> subjects;
    private boolean retake;

    /**
    * Constructor that makes the exact number of semesters.
    *
    * @param numberOfSemesters number of semesters.
    */
    public Subjects(int numberOfSemesters) {
        this.retake = false;
        this.subjects = new ArrayList<>();
        for (int i = 0; i < numberOfSemesters; i++) {
            this.subjects.add(new HashMap<>());
        }
    }

    /**
    * Getter for retake flag.
    *
    * @return retake flag.
    */
    public boolean getRetake() {
        return this.retake;
    }

    /**
     * Getter for the whole table.
     *
     * @return subjects list.
     */
    public List<Map<String, Mark>> getList() {
        return subjects;
    }

    /**
    * Adds mark.
    *
    * @param semester semester where we need to add mark.
    * @param subject subject with mark we add.
    * @param mark mark itself.
    */
    public void add(int semester, String subject, Mark mark) {
        if (this.getSemester(semester).containsKey(subject)) {
            retake = true;
        }
        this.subjects.get(semester - 1).put(subject, mark);
    }

    /**
    * Getter for particular semester.
    *
    * @param semester number of semester.
    *
    * @return map of the semester.
    */
    public Map<String, Mark> getSemester(int semester) {
        return subjects.get(semester - 1);
    }

    /**
    * Getter for particular mark.
    *
    * @param semester number of semester.
    * @param subject name of subject.
    *
    * @return Mark.
    */
    public Mark getMark(int semester, String subject) {
        if (!this.getSemester(semester).containsKey(subject)) {
            System.out.println("There is no such subject");
            return null;
        }
        return this.getSemester(semester).getOrDefault(subject, Mark.NOT_STATED);
    }
}
