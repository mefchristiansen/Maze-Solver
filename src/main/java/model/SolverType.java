package model;

public enum SolverType {
    BFS("BFS"),
    DFS("DFS"),
    AStar("A*");

    private final String name;

    SolverType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SolverType fromString(String name) {
        for (SolverType solverType : SolverType.values()) {
            if (solverType.name.equalsIgnoreCase(name)) {
                return solverType;
            }
        }
        return null;
    }
}
