package ru.nsu.kislitsyn;

import java.util.ArrayList;


public class Matrix<LineT, ColumnT> {
    private final ArrayList<Line<LineT, ColumnT>> matrix;

    public Matrix() {
        this.matrix = new ArrayList<>();
    }

    public ArrayList<Line<LineT, ColumnT>> getMatrix() {
        return this.matrix;
    }

    public ArrayList<ColumnT> getLine(int index) {
        if (this.matrix.isEmpty()) {
            return null;
        } else {
            return matrix.get(index).getColumns();
        }
    }

    public Line<LineT, ColumnT> getLineValue(int index) {
        if (this.matrix.isEmpty()) {
            return null;
        } else {
            return matrix.get(index);
        }
    }

    public void addColumn(ColumnT columnToAdd) {
        for (Line<LineT, ColumnT> line : this.matrix) {
            line.add(columnToAdd);
        }
    }

    public void addLine(LineT lineValue) {
        this.matrix.add(new Line<>(lineValue));
    }

    public void removeColumnByIndex(int index) {
        for (Line<LineT, ColumnT> line : this.matrix) {
            line.removeByIndex(index);
        }
    }

    public void removeColumnByValue(ColumnT value) {
        for (Line<LineT, ColumnT> line : this.matrix) {
            line.removeByValue(value);
        }
    }

    public void removeLineByValue(LineT value) {
        this.matrix.removeIf((Line<LineT, ColumnT> line) -> line.getValue().equals(value));
    }

    public void removeLineByIndex(int index) {
        this.matrix.remove(index);
    }

    public int indexOfLine(LineT value) {
        for (int i = 0; i < this.matrix.size(); i++) {
            if (matrix.get(i).getValue().equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public ColumnT getColumnByIndex(int indexOfLine, int indexOfColumn) {
        return this.getLineValue(indexOfColumn).getColumnByIndex(indexOfColumn);
    }

}