package ru.nsu.kislitsyn.studentsBook;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class StudentsBook {
    private final int NUMBER_OF_SEMESTERS = 8;
    private String firstname;
    private String surname;
    private int groupNumber;
    private final ArrayList<HashMap<String, Mark>> subjects;
//    private final HashMap<String, SemesterAndMark> finalMarks;
    private Mark qualificationTask;

    public StudentsBook(String firstname, String surname, int groupNumber) {
        this.firstname = firstname;
        this.surname = surname;
        this.groupNumber = groupNumber;
//        this.subjects = new HashMap<>();
        this.subjects = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_SEMESTERS; i++) {
            this.subjects.add(new HashMap<>());
        }
//        this.finalMarks = new HashMap<>();
    }

    private record SemesterAndMark(int semester, Mark mark) {}
    public String getFirstname() {
        return firstname;
    }

//    public HashMap<Integer, HashMap<String, Mark>> getSubjects() {
    public ArrayList<HashMap<String, Mark>> getSubjects() {
        return this.subjects;
    }
    public String getSurname() {
        return surname;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setQualificationTask(Mark mark) {
        this.qualificationTask = mark;
    }

    public Mark getQualificationTask() {
        return this.qualificationTask;
    }

    public void addSubject(Integer semesterNumber, String subject) {
        this.subjects.get(semesterNumber - 1).put(subject, Mark.NOT_STATED);
    }

    public void addTotalMark(Integer semesterNumber, String subject, Mark mark) {
        this.subjects.get(semesterNumber - 1).put(subject, mark);
    }

    public void addQualificationMark(Mark mark) {
        this.qualificationTask = mark;
    }


    public double averageMark() {
        return subjects.stream()
                .flatMapToInt(subject -> subject.values().stream().mapToInt(Mark::getMark))
                .summaryStatistics()
                .getAverage();
    }

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
