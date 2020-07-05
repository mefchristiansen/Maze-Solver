package model;

/**
 * All of the different maze solver types.
 */
public enum SolverType {
	BFS("BFS"),
	DFS("DFS"),
	AStar("A*");

	private final String name;

	SolverType(String name) {
		this.name = name;
	}

	/**
	 * Returns the enum value corresponding to the input string (if it exists).
	 *
	 * @param name A string
	 * @return The SolverType with the name corresponding to the input string (if it exists).
	 */
	public static SolverType fromString(String name) {
		for (SolverType solverType : SolverType.values()) {
			if (solverType.name.equalsIgnoreCase(name)) {
				return solverType;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}
}
