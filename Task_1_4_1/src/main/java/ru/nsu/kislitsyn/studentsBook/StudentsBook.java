package ru.nsu.kislitsyn.studentsBook;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
* Class that solves the task.
*/
public class StudentsBook {
    private final String firstname;
    private final String surname;
    private final int groupNumber;
    private final ArrayList<HashMap<String, Mark>> subjects;
    private Mark qualificationTask;

    /**
    * Constructor of the class.
    *
    * @param firstname name of the student.
    * @param surname surname of the student.
    * @param groupNumber number of student's group.
    */
    public StudentsBook(String firstname, String surname, int groupNumber) {
        this.firstname = firstname;
        this.surname = surname;
        this.groupNumber = groupNumber;
        this.subjects = new ArrayList<>();
        int NUMBER_OF_SEMESTERS = 8;
        for (int i = 0; i < NUMBER_OF_SEMESTERS; i++) {
            this.subjects.add(new HashMap<>());
        }
    }

    /**
    * Getter of name.
    *
    * @return firstname.
    */
    public String getFirstname() {
        return firstname;
    }

    /**
    * Getter of subjects hashmap.
    *
    * @return this.subjects.
    */
    public ArrayList<HashMap<String, Mark>> getSubjects() {
        return this.subjects;
    }

    /**
    * Getter for surname.
    *
    * @return surname.
    */
    public String getSurname() {
        return surname;
    }

    /**
    * Getter of group number.
    *
    * @return number of group.
    */
    public int getGroupNumber() {
        return groupNumber;
    }

    /**
    * Setter for mark of qualification task.
    *
    * @param mark mark of qualification task.
    */
    public void setQualificationTask(Mark mark) {
        this.qualificationTask = mark;
    }

    /**
    * Getter for mark of qualification task.
    *
    * @return mark of qualification task.
    */
    public Mark getQualificationTask() {
        return this.qualificationTask;
    }

    /**
    * Adds subject to the hashmap.
    *
    * @param semesterNumber number of semester.
    * @param subject name of the subject.
    */
    public void addSubject(Integer semesterNumber, String subject) {
        this.subjects.get(semesterNumber - 1).put(subject, Mark.NOT_STATED);
    }

    /**
    * Adds mark for the subject (and adds subject if needed).
    *
    * @param semesterNumber number of semester.
    * @param subject name of subject.
    * @param mark mark for the subject.
    */
    public void addTotalMark(Integer semesterNumber, String subject, Mark mark) {
        this.subjects.get(semesterNumber - 1).put(subject, mark);
    }

    /**
    * Computes the average of all marks.
    *
    * @return average mark.
    */
    public double averageMark() {
        return subjects.stream()
                .flatMapToInt(subject -> subject.values().stream().mapToInt(Mark::getMark))
                .summaryStatistics()
                .getAverage();
    }

    /**
    * Checks if student can get red diploma.
    *
    * @return true if student still can get red diploma, otherwise false.
    */
    public boolean redDiploma() {
        if (qualificationTask != Mark.EXCELLENT) {
            return false;
        }
        int cntOfExcellent = 0;
        int cntMarks = 0;
        HashSet<String> finalSubjects = new HashSet<>();
        int mark;
        for (int i = subjects.size() - 1; i >= 0; i--) {
            for (String subjectName : subjects.get(i).keySet()) {
                if (!finalSubjects.contains(subjectName)) {
                    finalSubjects.add(subjectName);
                    cntMarks++;
                    mark = subjects.get(i).get(subjectName).getMark();
                    if (mark < 4) {
                        return false;
                    } else {
                        if (mark == 5) {
                            cntOfExcellent++;
                        }
                    }
                }
            }
        }
        return !((double) cntOfExcellent / cntMarks < 0.75);
    }

    /**
     * Checks if student receives scholarship in particular semester.
     *
     * @param semester the semester we need to check.
     *
     * @return false if student won't have scholarship.
     */
    public boolean receiveScholarship(int semester) {
        if (semester == 1) {
            return true;
        }
        for (Mark mark : subjects.get(semester - 2).values()) {
            if (mark.getMark() < 4) {
                return false;
            }
        }
        return true;
    }


}
