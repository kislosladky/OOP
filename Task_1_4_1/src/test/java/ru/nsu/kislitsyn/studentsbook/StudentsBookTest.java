package ru.nsu.kislitsyn.studentsbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class StudentsBookTest {
    @Test
    void constructorTest() {
        StudentsBook book = new StudentsBook("Ilya", "Kislitsyn", 22213);
        assertEquals("Ilya", book.getFirstname());
        assertEquals("Kislitsyn", book.getSurname());
        assertEquals(22213, book.getGroupNumber());
    }

    @Test
    void qualiMarktest() {
        StudentsBook book = new StudentsBook("Ilya", "Kislitsyn", 22213);

        book.setQualificationTask(Mark.EXCELLENT);

        assertEquals(5, book.getQualificationTask().getMark());
    }

    @Test
    void addSubjectTest() {
        StudentsBook book = new StudentsBook("Ilya", "Kislitsyn", 22213);

        book.addTotalMark(1, "PE", Mark.EXCELLENT);
        book.addTotalMark(1, "Math", Mark.ACCEPTABLE);
        book.addTotalMark(1, "Imperative programming", Mark.GOOD);
        book.addTotalMark(1, "Functional programming", Mark.EXCELLENT);

        assertEquals(4, book.getSubjects().get(0).size());
    }

    @Test
    void scholarshipTest() {
        StudentsBook book = new StudentsBook("Ilya", "Kislitsyn", 22213);

        book.addTotalMark(1, "PE", Mark.EXCELLENT);
        book.addTotalMark(1, "Math", Mark.ACCEPTABLE);
        book.addTotalMark(1, "Imperative programming", Mark.GOOD);
        book.addTotalMark(1, "Functional programming", Mark.EXCELLENT);

        assertFalse(book.receiveScholarship(2));
        assertTrue(book.receiveScholarship(1));
    }


    @Test
    void averageMarkTest() {
        StudentsBook book = new StudentsBook("Ilya", "Kislitsyn", 22213);

        book.addTotalMark(1, "PE", Mark.EXCELLENT);
        book.addTotalMark(1, "Math", Mark.ACCEPTABLE);
        book.addTotalMark(1, "Imperative programming", Mark.GOOD);
        book.addTotalMark(1, "Functional programming", Mark.EXCELLENT);
        book.addTotalMark(2, "Functional programming", Mark.ACCEPTABLE);
        book.addTotalMark(2, "Math", Mark.GOOD);
        book.addTotalMark(2, "PE", Mark.GOOD);
        book.addTotalMark(2, "Imperative programming", Mark.GOOD);
        book.addTotalMark(2, "English", Mark.GOOD);
        book.addTotalMark(2, "Imperative programming", Mark.GOOD);

        assertEquals(4, book.averageMark());
    }

    @Test
    void diplomaFalseTest() {
        StudentsBook book = new StudentsBook("Ilya", "Kislitsyn", 22213);

        book.addTotalMark(1, "PE", Mark.EXCELLENT);
        book.addTotalMark(1, "Math", Mark.ACCEPTABLE);
        book.addTotalMark(1, "Imperative programming", Mark.GOOD);
        book.addTotalMark(1, "Functional programming", Mark.EXCELLENT);
        book.addTotalMark(2, "Functional programming", Mark.ACCEPTABLE);
        book.addTotalMark(2, "Math", Mark.GOOD);
        book.addTotalMark(2, "PE", Mark.GOOD);
        book.addTotalMark(2, "Imperative programming", Mark.GOOD);
        book.addTotalMark(2, "English", Mark.GOOD);
        book.addTotalMark(2, "Imperative programming", Mark.GOOD);
        book.setQualificationTask(Mark.EXCELLENT);

        assertFalse(book.redDiploma());
    }

    @Test
    void diplomaTrueTest() {
        StudentsBook book = new StudentsBook("Ilya", "Kislitsyn", 22213);

        book.addTotalMark(1, "PE", Mark.GOOD);
        book.addTotalMark(1, "Math", Mark.GOOD);
        book.addTotalMark(1, "Imperative programming", Mark.EXCELLENT);
        book.addTotalMark(1, "Functional programming", Mark.GOOD);
        book.addTotalMark(2, "Functional programming", Mark.EXCELLENT);
        book.addTotalMark(2, "Math", Mark.GOOD);
        book.addTotalMark(2, "PE", Mark.EXCELLENT);
        book.addTotalMark(2, "Imperative programming", Mark.EXCELLENT);
        book.addTotalMark(2, "English", Mark.EXCELLENT);
        book.addTotalMark(2, "Philosophy", Mark.EXCELLENT);

        book.setQualificationTask(Mark.EXCELLENT);


        assertTrue(book.redDiploma());
    }


}