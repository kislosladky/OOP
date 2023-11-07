package ru.nsu.kislitsyn;

import java.util.ArrayList;
import java.util.Objects;

public class Line<LineT, ColumnT> {
    private LineT value;
    private final ArrayList<ColumnT> columns;


    public ArrayList<ColumnT> getColumns() {
        return columns;
    }

    Line(LineT value) {
        this.value = value;
        this.columns = new ArrayList<>();
    }

    Line(LineT value, ArrayList<ColumnT> columns) {
        this.value = value;
        this.columns = columns;
    }

    void add(ColumnT column) {
        this.columns.add(column);
    }

    void removeByIndex(int index) {
        this.columns.remove(index);
    }

    void removeByValue(ColumnT value) {
        this.columns.removeIf((ColumnT columnValue) -> columnValue.equals(value));
    }

    public LineT getValue() {
        return value;
    }

    public void setValue(LineT value) {
        this.value = value;
    }

    public void setColumn(int index, ColumnT value) {
        columns.set(index, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Line<?, ?> line)) {
            return false;
        }
        return Objects.equals(getValue(), line.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}