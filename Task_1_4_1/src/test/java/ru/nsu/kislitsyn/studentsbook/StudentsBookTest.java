package ru.nsu.kislitsyn.studentsbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class StudentsBookTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private StudentsBook initStudentsBook() {
        return new StudentsBook("Ilya", "Kislitsyn", 22213, 8);

    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void constructorTest() {
        StudentsBook book = initStudentsBook();
        assertEquals("Ilya", book.getFirstname());
        assertEquals("Kislitsyn", book.getSurname());
        assertEquals(22213, book.getGroupNumber());
        assertEquals(Mark.NOT_STATED, book.getDiplomaWork());
        assertEquals(8, book.getDurationOfStudying());
        assertEquals(8, book.getSubjects().size());
    }

    @Test
    void qualiMarktest() {
        StudentsBook book = initStudentsBook();
        book.setDiplomaWork(Mark.EXCELLENT);

        assertEquals(5, book.getDiplomaWork().getMark());
    }

    @Test
    void addTotalMarkTest() {
        StudentsBook book = initStudentsBook();
        book.addTotalMark(1, "PE", Mark.EXCELLENT);
        book.addTotalMark(1, "Math", Mark.ACCEPTABLE);
        book.addTotalMark(1, "Imperative programming", Mark.GOOD);
        book.addTotalMark(1, "Functional programming", Mark.EXCELLENT);

        assertEquals(4, book.getSubjects().get(0).size());
    }


    @Test
    void addTotalMarkWithToZeroSemester() {
        StudentsBook book = initStudentsBook();
        book.addTotalMark(0, "PE", Mark.EXCELLENT);
        assertEquals("Number of semester is out of bound", outputStreamCaptor.toString().trim());
        book.addTotalMark(9, "Math", Mark.ACCEPTABLE);
    }

    @Test
    void addTotalMarkWithToLargeSemester() {
        StudentsBook book = initStudentsBook();
        book.addTotalMark(9, "PE", Mark.EXCELLENT);
        assertEquals("Number of semester is out of bound", outputStreamCaptor.toString().trim());
    }

    @Test
    void scholarshipTest() {
        StudentsBook book = initStudentsBook();

        book.addTotalMark(1, "PE", Mark.EXCELLENT);
        book.addTotalMark(1, "Math", Mark.ACCEPTABLE);
        book.addTotalMark(1, "Imperative programming", Mark.GOOD);
        book.addTotalMark(1, "Functional programming", Mark.EXCELLENT);

        assertFalse(book.receiveScholarship(2));
        assertTrue(book.receiveScholarship(1));


    }

    @Test
    void scholarshipWithZeroSemesterTest() {
        StudentsBook book = initStudentsBook();

        book.addTotalMark(1, "PE", Mark.EXCELLENT);
        book.addTotalMark(1, "Math", Mark.ACCEPTABLE);
        book.addTotalMark(1, "Imperative programming", Mark.GOOD);
        book.addTotalMark(1, "Functional programming", Mark.EXCELLENT);

        assertFalse(book.receiveScholarship(0));
        assertEquals("Number of semester is out of bound", outputStreamCaptor.toString().trim());
    }

    @Test
    void scholarshipWithLargeSemesterTest() {
        StudentsBook book = initStudentsBook();

        book.addTotalMark(1, "PE", Mark.EXCELLENT);
        book.addTotalMark(1, "Math", Mark.ACCEPTABLE);
        book.addTotalMark(1, "Imperative programming", Mark.GOOD);
        book.addTotalMark(1, "Functional programming", Mark.EXCELLENT);

        assertFalse(book.receiveScholarship(9));
        assertEquals("Number of semester is out of bound", outputStreamCaptor.toString().trim());
    }


    @Test
    void averageMarkTest() {
        StudentsBook book = initStudentsBook();

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
        StudentsBook book = initStudentsBook();

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
        book.setDiplomaWork(Mark.EXCELLENT);

        assertFalse(book.redDiploma());
    }

    @Test
    void diplomaTrueTest() {
        StudentsBook book = initStudentsBook();

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

        book.setDiplomaWork(Mark.EXCELLENT);


        assertTrue(book.redDiploma());
    }

    @Test
    void diplomaFalseWithBadDiplomaWorkTest() {
        StudentsBook book = initStudentsBook();

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

        book.setDiplomaWork(Mark.GOOD);


        assertFalse(book.redDiploma());
    }


    @Test
    void getMarkTest() {
        StudentsBook book = initStudentsBook();

        book.addTotalMark(1, "Imperative programming", Mark.EXCELLENT);

        assertEquals(Mark.EXCELLENT, book.getMark(1, "Imperative programming"));
    }

    @Test
    void getMarkWithSemesterOutOfBound() {
        StudentsBook book = initStudentsBook();
        book.addTotalMark(1, "Imperative programming", Mark.EXCELLENT);

        assertNull(book.getMark(0, "Imperative programming"));
        assertNull(book.getMark(9, "Imperative programming"));
    }

    @Test
    void getMarkWithUnknownSubject() {
        StudentsBook book = initStudentsBook();
        book.addTotalMark(1, "Imperative programming", Mark.EXCELLENT);

        assertNull(book.getMark(1, "PE"));
    }

}