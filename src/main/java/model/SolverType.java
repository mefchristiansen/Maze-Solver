package model;

public enum SolverType {
    BFS("BFS"),
    DFS("DFS"),
    AStar("A*");

    private String name;

    SolverType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
