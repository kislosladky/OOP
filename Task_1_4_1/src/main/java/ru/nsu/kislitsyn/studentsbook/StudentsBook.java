package ru.nsu.kislitsyn.studentsbook;

import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;


/**
* Class that solves the task.
*/
public class StudentsBook {
    private final int durationOfStudying;
    private final String firstname;
    private final String surname;
    private final int groupNumber;
    private final Subjects subjects;
    private Mark diplomaWork;

    /**
    * Constructor of the class.
    *
    * @param firstname name of the student.
    * @param surname surname of the student.
    * @param groupNumber number of student's group.
    */
    public StudentsBook(String firstname,
                        String surname,
                        int groupNumber,
                        int durationOfStudying) {
        this.firstname = firstname;
        this.surname = surname;
        this.groupNumber = groupNumber;
        this.subjects = new Subjects(durationOfStudying);
        this.durationOfStudying = durationOfStudying;
        this.diplomaWork = Mark.NOT_STATED;
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
    public Subjects getSubjects() {
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
    public void setDiplomaWork(Mark mark) {
        this.diplomaWork = mark;
    }

    /**
    * Getter for mark of qualification task.
    *
    * @return mark of qualification task.
    */
    public Mark getDiplomaWork() {
        return this.diplomaWork;
    }

    /**
     * Getter of the number of semesters.
     *
     * @return the duration of studying in semesters.
     */
    public int getDurationOfStudying() {
        return durationOfStudying;
    }

    /**
     * Getter for some particular mark.
     *
     * @param semesterNumber number of semester.
     * @param subject name of the subject.
     *
     * @return a mark for the subject.
     */
    public Mark getMark(int semesterNumber, String subject) {
        if (semesterNumber > durationOfStudying || semesterNumber < 1) {
            System.out.println("Number of semester is out of bound");
            return null;
        }

        return this.subjects.getMark(semesterNumber, subject);
    }

    /**
    * Adds mark for the subject (and adds subject if needed).
    *
    * @param semesterNumber number of semester.
    * @param subject name of subject.
    * @param mark mark for the subject.
    */
    public void addTotalMark(Integer semesterNumber, String subject, Mark mark) {
        if (semesterNumber > durationOfStudying || semesterNumber < 1) {
            System.out.println("Number of semester is out of bound");
            return;
        }
        this.subjects.add(semesterNumber, subject, mark);
    }

    /**
    * Computes the average of all marks.
    *
    * @return average mark.
    */
    public double averageMark() {
        return subjects.getList().stream()
                .flatMapToInt(subject -> subject.values().stream()
                        .filter(mark -> mark != Mark.PASS && mark != Mark.NOT_STATED)
                        .mapToInt(Mark::getMark))
                .summaryStatistics()
                .getAverage();
    }

    /**
    * Checks if student can get red diploma.
    *
    * @return true if student still can get red diploma, otherwise false.
    */
    public boolean redDiploma() {
        if (diplomaWork != Mark.EXCELLENT) {
            return false;
        }

        IntSummaryStatistics stats = this.subjects.getList().stream()
                .flatMap(hashmap -> hashmap.entrySet().stream())
                .filter(pair -> pair.getValue() != Mark.NOT_STATED
                        && pair.getValue() != Mark.PASS)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (exist, replace) -> replace))
                .values().stream()
                .mapToInt(Mark::getMark).summaryStatistics();

        return stats.getMin() >= 4 && stats.getAverage() >= 4.75;
    }

    /**
     * Checks if student receives scholarship in particular semester.
     * Here I suppose that every student studies for free, just like on out course.
     *
     * @param semester the semester we need to check.
     *
     * @return false if student won't have scholarship.
     */
    public boolean receiveScholarship(int semester) {
        if (semester < 1 || semester > durationOfStudying) {
            System.out.println("Number of semester is out of bound");
            return false;
        }
        if (semester == 1) {
            return true;
        }

        // -1, because we are checking for the previous semester
        return subjects.getSemester(semester - 1).values().stream()
                .mapToInt(Mark::getMark).summaryStatistics().getMin() >= 4 && !subjects.getRetake();
    }
}
