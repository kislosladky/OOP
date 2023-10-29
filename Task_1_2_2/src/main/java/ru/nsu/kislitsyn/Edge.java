package ru.nsu.kislitsyn;

public record Edge<T>(Vertice<T> from, Vertice<T> to, int weight) {}
